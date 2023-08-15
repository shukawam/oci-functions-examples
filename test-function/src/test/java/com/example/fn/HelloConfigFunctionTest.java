package com.example.fn;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Rule;
import org.junit.Test;

import com.fnproject.fn.testing.FnResult;
import com.fnproject.fn.testing.FnTestingRule;

public class HelloConfigFunctionTest {

    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

    @Test
    public void shouldReturnDefaultGreeting() {
        testing.givenEvent().enqueue();
        testing.thenRun(HelloConfigFunction.class, "handleRequest");

        FnResult result = testing.getOnlyResult();
        assertEquals("Hello, world!", result.getBodyAsString());
    }

    @Test
    public void shouldReturnGreetingWithConfig() {
        testing.setConfig("greet", "Hi").givenEvent().withBody("John").enqueue();
        testing.thenRun(HelloConfigFunction.class, "handleRequest");

        FnResult result = testing.getOnlyResult();
        assertEquals("Hi, John!", result.getBodyAsString());
    }

    @Test
    public void shouldReturnGreetingWithConstructorConfig() {
        testing.setConfig("greet", "Hi").givenEvent().withBody("John").enqueue();
        testing.thenRun(HelloConstructorConfigFunction.class, "handleRequest");

        FnResult result = testing.getOnlyResult();
        assertEquals("Hi, John!", result.getBodyAsString());
    }

}
