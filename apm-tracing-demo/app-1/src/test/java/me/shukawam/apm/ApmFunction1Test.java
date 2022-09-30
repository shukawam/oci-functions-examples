package me.shukawam.apm;

import com.fnproject.fn.testing.*;
import org.junit.*;

import static org.junit.Assert.*;

public class ApmFunction1Test {

    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

    @Test
    public void shouldReturnGreeting() {
        testing
            .givenEvent()
            .withHeader("Fn-Http-Request-Url", "https://www.example.com/http-gateway-ctx")
            .withHeader("Fn-Http-Method", "GET")
            .enqueue();
        testing.thenRun(ApmFunction1.class, "handleRequest");

        FnResult result = testing.getOnlyResult();
        assertEquals("ok", result.getBodyAsString());
    }

}