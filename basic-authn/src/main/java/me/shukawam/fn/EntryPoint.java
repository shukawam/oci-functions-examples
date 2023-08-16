package me.shukawam.fn;

import java.util.logging.Logger;

import com.fnproject.fn.api.FnConfiguration;
import com.fnproject.fn.api.RuntimeContext;
import com.fnproject.fn.api.tracing.TracingContext;
import com.github.kristofa.brave.IdConversion;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.TraceContext;
import brave.sampler.Sampler;
import me.shukawam.fn.data.AuthNRequest;
import me.shukawam.fn.data.AuthNResponse;
import zipkin2.reporter.Sender;
import zipkin2.reporter.brave.AsyncZipkinSpanHandler;
import zipkin2.reporter.urlconnection.URLConnectionSender;

/**
 * @author shukawam
 */
public class EntryPoint {

    private static final Logger logger = Logger.getLogger(EntryPoint.class.getName());

    private Sender sender;
    private AsyncZipkinSpanHandler zipkinSpanHandler;
    private Tracing tracing;
    private Tracer tracer;
    private String apmUrl;
    private TraceContext traceContext;

    private String compartmentId;
    private String tableId;

    @FnConfiguration
    public void config(RuntimeContext ctx) {
        compartmentId = ctx.getConfigurationByKey("COMPARTMENT_ID").orElse("default");
        tableId = ctx.getConfigurationByKey("TABLE_ID").orElse("default");
    }

    public void intializeZipkin(TracingContext tracingContext) throws Exception {
        logger.info("Initializing the variables");
        apmUrl = tracingContext.getTraceCollectorURL();
        logger.info(String.format("apmUrl: %s", apmUrl));
        sender = URLConnectionSender.create(apmUrl);
        zipkinSpanHandler = AsyncZipkinSpanHandler.create(sender);
        tracing = Tracing.newBuilder()
                .localServiceName(tracingContext.getServiceName())
                .sampler(Sampler.NEVER_SAMPLE)
                .addSpanHandler(zipkinSpanHandler)
                .build();
        tracer = tracing.tracer();
        tracing.setNoop(!tracingContext.isTracingEnabled());
        traceContext = TraceContext.newBuilder()
                .traceId(IdConversion.convertToLong(tracingContext.getTraceId()))
                .spanId(IdConversion.convertToLong(tracingContext.getSpanId()))
                .sampled(tracingContext.isSampled()).build();
    }

    public AuthNResponse handleRequest(AuthNRequest request, RuntimeContext ctx, TracingContext tracingContext) {
        logger.info(String.format("appName: %s", tracingContext.getAppName()));
        logger.info(String.format("functionName: %s", tracingContext.getFunctionName()));
        logger.info(String.format("collectorUrl: %s", tracingContext.getTraceCollectorURL()));
        try {
            intializeZipkin(tracingContext);
            Span span = tracer.newChild(traceContext).name("MainHandle").start();
            if (!BasicAuthNUtils.isValidInput(request)) {
                return new AuthNResponse(false, "Basic realm=\"Input format is invalid.\"");
            }
            var scheme = request.getData().getToken().split(" ")[0];
            var token = request.getData().getToken().split(" ")[1];
            if (!BasicAuthNUtils.isValidAuthNSchema(scheme)) {
                return new AuthNResponse(false, "Basic realm=\"Schema is invalid.\"");
            }
            try (Tracer.SpanInScope ws = tracer.withSpanInScope(span)) {
                if (!new BasicAuthNProvider().basicAuthNWithNoSQL(token, compartmentId, tableId)) {
                    return new AuthNResponse(false, "Basic realm=\"Username or Password is invalid.\"");
                }
                return new AuthNResponse(true);
            } catch (RuntimeException | Error e) {
                span.error(e);
                throw e;
            } finally {
                span.finish();
                tracing.close();
                zipkinSpanHandler.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
