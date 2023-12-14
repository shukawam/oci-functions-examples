package me.shukawam;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.fnproject.fn.api.FnConfiguration;
import com.fnproject.fn.api.RuntimeContext;
import com.oracle.bmc.auth.AbstractAuthenticationDetailsProvider;
import com.oracle.bmc.auth.InstancePrincipalsAuthenticationDetailsProvider;
import com.oracle.bmc.auth.ResourcePrincipalAuthenticationDetailsProvider;
import com.oracle.bmc.streaming.StreamClient;
import com.oracle.bmc.streaming.model.PutMessagesDetails;
import com.oracle.bmc.streaming.model.PutMessagesDetailsEntry;
import com.oracle.bmc.streaming.requests.PutMessagesRequest;
import com.oracle.bmc.streaming.responses.PutMessagesResponse;

public class EntryPoint {
    private static final Logger LOGGER = Logger.getLogger(EntryPoint.class.getName());
    private AbstractAuthenticationDetailsProvider provider;
    private StreamClient client;
    private String streamOcid;

    @FnConfiguration
    public void config(RuntimeContext ctx) throws IOException {
        var isLocal = ctx.getConfigurationByKey("LOCAL").get();
        LOGGER.info(isLocal);
        if ("true".equals(isLocal)) {
            provider = InstancePrincipalsAuthenticationDetailsProvider.builder().build();
        } else {
            provider = ResourcePrincipalAuthenticationDetailsProvider.builder().build();
        }
        var streamingEndpoint = ctx.getConfigurationByKey("STREAMING_ENDPOINT")
                .orElse("https://cell-1.streaming.ap-tokyo-1.oci.oraclecloud.com");
        streamOcid = ctx.getConfigurationByKey("STREAMING_OCID").orElse("");
        LOGGER.info(streamingEndpoint);
        LOGGER.info(streamOcid);
        client = StreamClient.builder().endpoint(streamingEndpoint).build(provider);
    }

    public Response handleRequest(Request request) {
        var response = putMessage(request.getMessage());
        if (response.getPutMessagesResult().getEntries() != null) {
            return new Response("ack");
        } else {
            return new Response("failed.");
        }
    }

    public PutMessagesResponse putMessage(String value) {
        List<PutMessagesDetailsEntry> messages = List.of(
                PutMessagesDetailsEntry.builder().value(value.getBytes()).build());
        LOGGER.info("Put message to streaming");
        return client.putMessages(
                PutMessagesRequest.builder().streamId(streamOcid).putMessagesDetails(
                        PutMessagesDetails.builder().messages(messages).build()).build());
    }
}
