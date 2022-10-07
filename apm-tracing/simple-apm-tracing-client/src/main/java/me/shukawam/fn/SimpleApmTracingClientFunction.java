package me.shukawam.fn;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.TraceContext;
import brave.sampler.Sampler;
import com.fnproject.fn.api.tracing.TracingContext;
import com.github.kristofa.brave.IdConversion;
import com.oracle.bmc.auth.ResourcePrincipalAuthenticationDetailsProvider;
import com.oracle.bmc.functions.FunctionsInvokeClient;
import com.oracle.bmc.functions.requests.InvokeFunctionRequest;
import com.oracle.bmc.functions.responses.InvokeFunctionResponse;
import zipkin2.reporter.Sender;
import zipkin2.reporter.brave.AsyncZipkinSpanHandler;
import zipkin2.reporter.urlconnection.URLConnectionSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * @author shukawam
 */
public class SimpleApmTracingClientFunction {
    private static final Logger LOGGER = Logger.getLogger(SimpleApmTracingClientFunction.class.getName());

    private Sender sender;
    private AsyncZipkinSpanHandler zipkinSpanHandler;
    private Tracing tracing;
    private Tracer tracer;
    private String apmUrl;
    private TraceContext traceContext;

    public String handleRequest(Request request, TracingContext ctx) {
        initializeZipkin(ctx);
        // Start a new trace or span within an existing trace representing an operation
        Span span = tracer.newChild(traceContext).name("MainHandle").start();
        LOGGER.info("Inside Java Simple Apm Client function.");
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(span)) {
            return invokeAnotherFunction(request);
        } catch (RuntimeException e) {
            span.error(e);
            throw e;
        } finally {
            span.finish();
            tracing.close();
            zipkinSpanHandler.flush();
        }
    }

    private void initializeZipkin(TracingContext tracingContext) {
        LOGGER.info("Initializing the variables");
        apmUrl = tracingContext.getTraceCollectorURL();
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
                .sampled(tracingContext.isSampled())
                .build();
    }

    private String invokeAnotherFunction(Request request) {
        ResourcePrincipalAuthenticationDetailsProvider provider = ResourcePrincipalAuthenticationDetailsProvider.builder().build();
        FunctionsInvokeClient client = FunctionsInvokeClient
                .builder()
                .endpoint(request.functionEndpoint)
                .build(provider);
        InvokeFunctionRequest invokeFunctionRequest = InvokeFunctionRequest
                .builder()
                .functionId(request.functionOcid)
                .invokeFunctionBody(null)
                .build();
        InvokeFunctionResponse invokeFunctionResponse = client.invokeFunction(invokeFunctionRequest);
        BufferedReader br = new BufferedReader(new InputStreamReader(invokeFunctionResponse.getInputStream()));
        StringBuilder sb = new StringBuilder();
        try {
            String functionResponse;
            while ((functionResponse = br.readLine()) != null) {
                sb.append(functionResponse);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
