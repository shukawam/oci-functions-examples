package com.example.fn;

import com.fnproject.fn.testing.*;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.List;

public class HelloFunctionTest {

    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

    @Test
    public void shouldReturnGreeting() {
        testing.givenEvent().enqueue();
        testing.thenRun(HelloFunction.class, "handleRequest");

        FnResult result = testing.getOnlyResult();
        assertEquals("Hello, world!", result.getBodyAsString());
    }

    @Test
    public void shouldReturnGreetingWithBody() {
        testing.givenEvent().withBody("John").enqueue();
        testing.thenRun(HelloFunction.class, "handleRequest");

        FnResult result = testing.getOnlyResult();
        assertEquals("Hello, John!", result.getBodyAsString());
    }

    @Test
    public void shouldReturnSameGreeting() {
        testing.givenEvent().enqueue(10);
        testing.thenRun(HelloFunction.class, "handleRequest");

        List<FnResult> results = testing.getResults();
        results.forEach(result -> {
            assertEquals("Hello, world!", result.getBodyAsString());
        });
    }

}
