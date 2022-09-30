package com.example.fn;

import com.example.fn.data.OpenSearchResponse;
import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HelloFunction {

    private final String OPENSEARCH_API_ENDPOINT = System.getenv().get("OPENSEARCH_API_ENDPOINT");
    private final String INDEX = System.getenv().get("INDEX");

    public Object handleRequest(HTTPGatewayContext ctx) {
        if (!ctx.getQueryParameters().get("q").isPresent()) {
            throw new RuntimeException("Query parameter is not set.");
        }
        String queryParameter = ctx.getQueryParameters().get("q").get();
        System.err.println(queryParameter);
        HttpClient httpClient = HttpClient.newBuilder().version(Version.HTTP_1_1).sslContext(insecureContext()).build();
        return search(httpClient, queryParameter);
    }

    private Object search(HttpClient httpClient, String value) {
        System.err.println(OPENSEARCH_API_ENDPOINT);
        System.err.println(INDEX);
        String query = """
            {
                \"size\": 25,
                \"query\": {
                    \"multi_match\": {
                        \"query\": "%s",
                        \"fields\": [\"firstname\", \"lastname\", \"address\", \"employer\", \"email\", \"city\"]
                    }
                }
            }""".formatted(value);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OPENSEARCH_API_ENDPOINT + "/" + INDEX + "/_search"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(query))
                .build();
        try {
            HttpResponse response = httpClient.send(request, BodyHandlers.ofString());
            Gson gson = new Gson();
            OpenSearchResponse openSearchResponse = gson.fromJson(response.body().toString(), OpenSearchResponse.class);
            return openSearchResponse.hits.hits;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("document is not found.");
        }
    }

    private SSLContext insecureContext() {
        TrustManager[] noopManagers = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String string) {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String string) {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }
        };
        try {
            SSLContext sc = SSLContext.getInstance("ssl");
            sc.init(null, noopManagers, null);
            return sc;
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            return null;
        }
    }

}
