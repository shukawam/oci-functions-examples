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
        if(ctx.getInvocationContext().getRequestHeaders().get("X-B3-Traceid").isPresent()) {
            var xB3TraceId = ctx.getInvocationContext().getRequestHeaders().get("X-B3-Traceid").get();
            ctx.setResponseHeader("X-B3-Traceid", xB3TraceId);
        }
        if(ctx.getInvocationContext().getRequestHeaders().get("X-B3-SpanId").isPresent()) {
            var xB3TraceId = ctx.getInvocationContext().getRequestHeaders().get("X-B3-SpanId").get();
            ctx.setResponseHeader("X-B3-SpanId", xB3TraceId);
        }
        if(ctx.getInvocationContext().getRequestHeaders().get("X-B3-ParentSpanId").isPresent()) {
            var xB3TraceId = ctx.getInvocationContext().getRequestHeaders().get("X-B3-ParentSpanId").get();
            ctx.setResponseHeader("X-B3-ParentSpanId", xB3TraceId);
        }
    }

    private void loggingHeaders(HTTPGatewayContext ctx) {
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