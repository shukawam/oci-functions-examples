package com.example.fn;

import com.oracle.bmc.auth.ResourcePrincipalAuthenticationDetailsProvider;
import com.oracle.bmc.functions.FunctionsInvokeClient;
import com.oracle.bmc.functions.requests.InvokeFunctionRequest;
import com.oracle.bmc.functions.responses.InvokeFunctionResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class HelloFunction {
    private static final Logger LOGGER = Logger.getLogger(HelloFunction.class.getName());

    public String handleRequest(Request request) {
        var endpoint = request.functionOcid;
        LOGGER.info(String.format("FUNCTIONS_ENDPOINT: %s", endpoint));
        var fnOcid = request.functionEndpoint;
        LOGGER.info(String.format("FUNCTIONS_OCID: %s", fnOcid));
        var name = request.name;
        LOGGER.info(String.format("NAME: %s", name));
        return invokeOtherFunctions(endpoint, fnOcid, name);
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
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}