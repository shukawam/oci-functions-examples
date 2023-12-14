package me.shukawam;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnproject.fn.testing.FnTestingRule;

public class EntryPointTest {

    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

    @Test
    public void sholdReturnAck() {
        var mapper = new ObjectMapper();
        try {
            testing
                    .setConfig("LOCAL", "true")
                    .setConfig("STREAMING_OCID", "ocid1.stream.oc1.ap-tokyo-1.amaaaaaassl65iqax4mlhw46vdc3ain5ackfpkn7pgtawyw6hdltg2qqpxva")
                    .setConfig("STREAMING_ENDPOINT", "https://cell-1.streaming.ap-tokyo-1.oci.oraclecloud.com")
                    .givenEvent()
                    .withHeader("content-type", "applicatoin/json")
                    .withBody(mapper.writeValueAsString(new Request("Hello world")))
                    .enqueue();
            testing.thenRun(EntryPoint.class, "handleRequest");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        var result = testing.getOnlyResult();
        var expected = "{\"status\":\"ack\"}";
        assertEquals(expected, result.getBodyAsString());
    }
}
