package com.example.fn;

import com.fnproject.fn.api.OutputEvent;
import com.fnproject.fn.testing.*;
import org.junit.*;
import static org.junit.Assert.*;

public class HelloFunctionTest {

    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

    // @Test
    public void connect_success() {
        testing.givenEvent()
            .withHeader("Fn-Http-Method", "GET")
            .withHeader("Fn-Http-Request-Url", "?q=Amber")
            .enqueue();
        testing.thenRun(HelloFunction.class, "handleRequest");
        FnResult result = testing.getOnlyResult();
        assertEquals(OutputEvent.Status.Success, result.getStatus());
    }

}