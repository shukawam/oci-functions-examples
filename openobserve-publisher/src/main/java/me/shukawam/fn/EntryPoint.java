package me.shukawam.fn;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnproject.fn.api.FnConfiguration;
import com.fnproject.fn.api.RuntimeContext;

import me.shukawam.fn.data.OpenObserveResponse;

public class EntryPoint {
    private static final Logger logger = Logger.getLogger(EntryPoint.class.getName());
    private static final HttpClient client = HttpClient.newHttpClient();
    private String endpoint;
    private String user;
    private String password;

    @FnConfiguration
    public void configure(RuntimeContext ctx) {
        endpoint = ctx.getConfigurationByKey("OPENOBSERVE_ENDPOINT").orElse("http://localhost:8080");
        user = ctx.getConfigurationByKey("USERNAME").orElse("default");
        password = ctx.getConfigurationByKey("PASSWORD").orElse("default");
    }

    public OpenObserveResponse handleRequest(List<Map<String, Object>> inputs) {
        inputs.forEach(i -> {
            logger.info(i.toString());
        });
        try {
            var request = HttpRequest.newBuilder(URI.create(String.format("%s/api/default/default/_json", endpoint)))
                    .header("Content-Type", "application/json")
                    .header("Authorization",
                            String.format("Basic %s",
                                    Base64.getEncoder()
                                            .encodeToString(String.format("%s:%s", user, password).getBytes())))
                    .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(inputs))).build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return convert(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to JSON Processing.");
        }
    }

    private OpenObserveResponse convert(String input) {
        try {
            return new ObjectMapper().readValue(input, OpenObserveResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to JSON Processing.");
        }

    }

}
