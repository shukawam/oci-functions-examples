package me.shukawam.fn;

import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;
import com.oracle.bmc.auth.ResourcePrincipalAuthenticationDetailsProvider;
import com.oracle.bmc.functions.FunctionsInvokeClient;
import com.oracle.bmc.functions.requests.InvokeFunctionRequest;
import com.oracle.bmc.functions.responses.InvokeFunctionResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class ClientFunction {
    private static final Logger LOGGER = Logger.getLogger(ClientFunction.class.getName());

    public String handleRequest(Request request, HTTPGatewayContext ctx) {
        var endpoint = request.functionEndpoint;
        LOGGER.info(String.format("FUNCTIONS_ENDPOINT: %s", endpoint));
        var fnOcid = request.functionOcid;
        LOGGER.info(String.format("FUNCTIONS_OCID: %s", fnOcid));
        var name = request.name;
        LOGGER.info(String.format("NAME: %s", name));
        loggingHeaders(ctx);
        tracingHeaderPropagation(ctx);
        return invokeOtherFunctions(endpoint, fnOcid, name);
    }

    private void tracingHeaderPropagation(HTTPGatewayContext ctx) {
        if (ctx.getHeaders().get("X-B3-TraceId").isPresent()) {
            var xB3TraceId = ctx.getHeaders().get("X-B3-TraceId").get();
            ctx.setResponseHeader("X-B3-TraceId", xB3TraceId);
        }
        if (ctx.getHeaders().get("X-B3-SpanId").isPresent()) {
            var xB3SpanId = ctx.getHeaders().get("X-B3-SpanId").get();
            ctx.setResponseHeader("X-B3-SpanId", xB3SpanId);
        }
        if (ctx.getHeaders().get("X-B3-ParentSpanId").isPresent()) {
            var xB3ParentSpanId = ctx.getHeaders().get("X-B3-ParentSpanId").get();
            ctx.setResponseHeader("X-B3-ParentSpanId", xB3ParentSpanId);
        }

    }

    private void loggingHeaders(HTTPGatewayContext ctx) {
//        LOGGER.info("*** HTTP Headers ***");
//        ctx.getHeaders().asMap().forEach((k,v) -> {
//            LOGGER.info(String.format("%s: %s", k, v));
//        });
        LOGGER.info("*** HTTP Request Headers ***");
        ctx.getInvocationContext().getRequestHeaders().asMap().forEach((k, v) -> LOGGER.info(String.format("%s: %s", k, v)));
    }

    private String invokeOtherFunctions(String endpoint, String fnOcid, String name) {
       ResourcePrincipalAuthenticationDetailsProvider provider = ResourcePrincipalAuthenticationDetailsProvider
                .builder()
                .build();
        FunctionsInvokeClient invokeClient = FunctionsInvokeClient
                .builder()
                .endpoint(endpoint)
                .build(provider);
        InputStream inputStream = new ByteArrayInputStream(name.getBytes(StandardCharsets.UTF_8));
        InvokeFunctionRequest request = InvokeFunctionRequest
                .builder()
                .functionId(fnOcid)
                .invokeFunctionBody(inputStream)
                .build();
        InvokeFunctionResponse response = invokeClient.invokeFunction(request);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getInputStream()));
        StringBuilder sb = new StringBuilder();
        try {
            String functionsResponse;
            while ((functionsResponse = br.readLine()) != null) {
                sb.append(functionsResponse);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }



}