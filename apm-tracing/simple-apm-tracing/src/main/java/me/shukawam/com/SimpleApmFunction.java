package me.shukawam.com;

import java.util.logging.Logger;
import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.*;
import brave.sampler.Sampler;
import com.fnproject.fn.api.tracing.TracingContext;
import com.github.kristofa.brave.IdConversion;
import zipkin2.reporter.Sender;
import zipkin2.reporter.brave.AsyncZipkinSpanHandler;
import zipkin2.reporter.urlconnection.URLConnectionSender;

/**
 * @author shukawam
 */
public class SimpleApmFunction {
    private static final Logger LOGGER = Logger.getLogger(SimpleApmFunction.class.getName());

    private Sender sender;
    private AsyncZipkinSpanHandler zipkinSpanHandler;
    private Tracing tracing;
    private Tracer tracer;
    private String apmUrl;
    private TraceContext traceContext;

    public String handleRequest(TracingContext tracingContext) {
        initializeZipkin(tracingContext);
        // Start a new trace or a span within an existing trace representing an operation
        Span span = tracer.newChild(traceContext).name("MainHandle").start();
        LOGGER.info("Inside Java Simple Apm function.");
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(span)) {
            method1();
            method2();
            method3();
        } catch (RuntimeException e) {
            span.error(e);
            throw e;
        } finally {
            span.finish();
            tracing.close();
            zipkinSpanHandler.flush();
        }
        return "Hello, AppName " + tracingContext.getAppName() + " :: fnName " + tracingContext.getFunctionName();
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

    private void method1() {
        LOGGER.info("Inside SimpleApmFunction#method1");
        TraceContext traceContext = tracing.currentTraceContext().get();
        Span span = tracer.newChild(traceContext).name("Method1").start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            span.finish();
        }
    }

    private void method2() {
        LOGGER.info("Inside SimpleApmFunction#method2");
        TraceContext traceContext = tracing.currentTraceContext().get();
        Span span = tracer.newChild(traceContext).name("Method2").start();
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            span.finish();
        }
    }

    private void method3() {
        LOGGER.info("Inside SimpleApmFunction#method3");
        TraceContext traceContext = tracing.currentTraceContext().get();
        Span span = tracer.newChild(traceContext).name("Method3").start();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            span.finish();
        }
    }
}
