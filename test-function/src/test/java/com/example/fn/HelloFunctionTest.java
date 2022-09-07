package com.example.fn;

import com.fnproject.fn.testing.*;
import org.junit.*;
import static org.junit.Assert.*;

public class HelloFunctionTest {

    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

    @Test
    public void shouldReturnGreeting() {
        testing
                .givenEvent()
                .enqueue();
        testing.thenRun(HelloFunction.class, "handleRequest");

        FnResult result = testing.getOnlyResult();
        assertEquals(true, result.isSuccess());
        assertEquals("Hello, world!", result.getBodyAsString());
    }

    @Test
    public void shouldReturnName() {
        testing
                .givenEvent()
                .withHeader("Fn-Http-Method", "POST")
                .withBody("Bob")
                .enqueue();
        testing.thenRun(HelloFunction.class, "handleRequest");

        var result = testing.getOnlyResult();
        assertEquals(true, result.isSuccess());
        assertEquals("Hello, Bob!", result.getBodyAsString());
    }

}
