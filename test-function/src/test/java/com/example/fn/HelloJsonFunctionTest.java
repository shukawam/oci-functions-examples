package com.example.fn;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnproject.fn.testing.FnResult;
import com.fnproject.fn.testing.FnTestingRule;

public class HelloJsonFunctionTest {
    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

    @Test
    public void shouldReturnGreetingWithBody() {
        var mapper = new ObjectMapper();
        try {
            testing
                .givenEvent()
                .withHeader("content-type", "application/json")
                .withBody(mapper.writeValueAsString(new Input("John")))
                .enqueue();
            testing.thenRun(HelloJsonFunction.class, "handleRequest");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        FnResult result = testing.getOnlyResult();
        assertEquals("Hello, John!", result.getBodyAsString());
    }
}
