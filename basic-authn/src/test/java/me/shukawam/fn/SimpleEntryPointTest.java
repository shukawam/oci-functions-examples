package me.shukawam.fn;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnproject.fn.testing.FnResult;
import com.fnproject.fn.testing.FnTestingRule;

import me.shukawam.fn.data.AuthNRequest;
import me.shukawam.fn.data.AuthNRequestDetail;
import me.shukawam.fn.data.AuthNResponse;

public class SimpleEntryPointTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

    @Test
    public void shouldReturnCorrectResponse() throws JsonProcessingException {
        var input = new AuthNRequest("USER_DEFINED", new AuthNRequestDetail("Basic ZGVmYXVsdDpkZWZhdWx0"));
        testing.givenEvent().withHeader("content-type", "application/json").withBody(mapper.writeValueAsString(input))
                .enqueue();
        testing.thenRun(SimpleEntryPoint.class, "handleRequest");
        FnResult result = testing.getOnlyResult();
        assertEquals(mapper.writeValueAsString(new AuthNResponse(true)), result.getBodyAsString());
    }

    @Test
    public void shouldReturnCorrectResponseWithNoSQL() throws JsonProcessingException {
        var input = new AuthNRequest("USER_DEFINED", new AuthNRequestDetail("Basic ZGVmYXVsdDpkZWZhdWx0"));
        testing
                .setConfig("compartment_id",
                        "ocid1.compartment.oc1..aaaaaaaanjtbllhqxcg67dq7em3vto2mvsbc6pbgk4pw6cx37afzk3tngmoa")
                .setConfig("table_id",
                        "ocid1.nosqltable.oc1.ap-tokyo-1.amaaaaaassl65iqaljn4eeasub32rfuc5xtq2pr7ovzyu6sksz5uu2aimxuq")
                .givenEvent().withHeader("content-type", "application/json").withBody(mapper.writeValueAsString(input))
                .enqueue();
        testing.thenRun(SimpleEntryPoint.class, "handleRequest");
        FnResult result = testing.getOnlyResult();
        assertEquals(mapper.writeValueAsString(new AuthNResponse(true)), result.getBodyAsString());
    }

    @Test
    public void shouldReturnNoTypeResponse() throws JsonProcessingException {
        var input = new AuthNRequest("", new AuthNRequestDetail("Basic ZGVmYXVsdDpkZWZhdWx0"));
        testing.givenEvent().withHeader("content-type", "application/json").withBody(mapper.writeValueAsString(input))
                .enqueue();
        testing.thenRun(SimpleEntryPoint.class, "handleRequest");
        FnResult result = testing.getOnlyResult();
        assertEquals(mapper.writeValueAsString(new AuthNResponse(false, "Basic realm=\"Input format is invalid.\"")),
                result.getBodyAsString());
    }

    @Test
    public void shouldReturnInvalidTypeResponse() throws JsonProcessingException {
        var input = new AuthNRequest("DUMMY", new AuthNRequestDetail("Basic ZGVmYXVsdDpkZWZhdWx0"));
        testing.givenEvent().withHeader("content-type", "application/json").withBody(mapper.writeValueAsString(input))
                .enqueue();
        testing.thenRun(SimpleEntryPoint.class, "handleRequest");
        FnResult result = testing.getOnlyResult();
        assertEquals(mapper.writeValueAsString(new AuthNResponse(false, "Basic realm=\"Input format is invalid.\"")),
                result.getBodyAsString());
    }

    @Test
    public void shouldReturnNoTokenResponse() throws JsonProcessingException {
        var input = new AuthNRequest("USER_DEFINED", new AuthNRequestDetail(""));
        testing.givenEvent().withHeader("content-type", "application/json").withBody(mapper.writeValueAsString(input))
                .enqueue();
        testing.thenRun(SimpleEntryPoint.class, "handleRequest");
        FnResult result = testing.getOnlyResult();
        assertEquals(mapper.writeValueAsString(new AuthNResponse(false, "Basic realm=\"Input format is invalid.\"")),
                result.getBodyAsString());
    }

    @Test
    public void shouldReturnInvalidSchemaResponse() throws JsonProcessingException {
        var input = new AuthNRequest("USER_DEFINED", new AuthNRequestDetail("Dummy ZGVmYXVsdDpkZWZhdWx0"));
        testing.givenEvent().withHeader("content-type", "application/json").withBody(mapper.writeValueAsString(input))
                .enqueue();
        testing.thenRun(SimpleEntryPoint.class, "handleRequest");
        FnResult result = testing.getOnlyResult();
        assertEquals(mapper.writeValueAsString(new AuthNResponse(false, "Basic realm=\"Schema is invalid.\"")),
                result.getBodyAsString());
    }

    @Test
    public void shouldReturnInvalidTokenResponse() throws JsonProcessingException {
        // Basic dummy:dummy
        var input = new AuthNRequest("USER_DEFINED", new AuthNRequestDetail("Basic ZHVtbXk6ZHVtbXk="));
        testing.givenEvent().withHeader("content-type", "application/json").withBody(mapper.writeValueAsString(input))
                .enqueue();
        testing.thenRun(SimpleEntryPoint.class, "handleRequest");
        FnResult result = testing.getOnlyResult();
        assertEquals(
                mapper.writeValueAsString(new AuthNResponse(false, "Basic realm=\"Username or Password is invalid.\"")),
                result.getBodyAsString());
    }
}
