package com.example.fn;

import com.fnproject.fn.api.InvocationContext;
import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;
import com.oracle.bmc.auth.InstancePrincipalsAuthenticationDetailsProvider;
import com.oracle.bmc.auth.ResourcePrincipalAuthenticationDetailsProvider;
import com.oracle.bmc.functions.FunctionsInvokeClient;
import com.oracle.bmc.functions.requests.InvokeFunctionRequest;
import com.oracle.bmc.functions.responses.InvokeFunctionResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class HelloFunction {
    private static final Logger LOGGER = Logger.getLogger(HelloFunction.class.getName());

    public String handleRequest(HTTPGatewayContext ctx) {
        var endpoint = ctx.getInvocationContext().getRuntimeContext().getConfiguration().get("FUNCTIONS_ENDPOINT");
        LOGGER.info(String.format("FUNCTIONS_ENDPOINT: %s", endpoint));
        var fnOcid = ctx.getInvocationContext().getRuntimeContext().getConfiguration().get("FUNCTIONS_OCID");
        LOGGER.info(String.format("FUNCTIONS_OCID: %s", fnOcid));
        var name = ctx.getQueryParameters().get("name").orElse("Joe");
        LOGGER.info(String.format("NAME: %s", name));
        return invokeOtherFunctions(endpoint, fnOcid, name);
    }

    private String invokeOtherFunctions(String endpoint, String fnOcid, String name) {
        LOGGER.info(String.format("START TIME: %s", System.currentTimeMillis()));
        // InstancePrincipalsAuthenticationDetailsProvider provider = InstancePrincipalsAuthenticationDetailsProvider
        //         .builder()
        //         .build();
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
                .body$(inputStream)
                .build();
        InvokeFunctionResponse response = invokeClient.invokeFunction(request);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getInputStream()));
        StringBuilder sb = new StringBuilder();
        try {
            String functionsResponse;
            while ((functionsResponse = br.readLine()) != null) {
                sb.append(functionsResponse);
            }
            LOGGER.info(String.format("RESPONSE: %s", sb));
            LOGGER.info(String.format("END TIME: %s", System.currentTimeMillis()));
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}