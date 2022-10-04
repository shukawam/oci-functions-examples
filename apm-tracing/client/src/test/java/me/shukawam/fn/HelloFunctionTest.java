package me.shukawam.fn;

import com.fnproject.fn.testing.*;
import org.junit.*;

import static org.junit.Assert.*;

public class HelloFunctionTest {

    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

    // @Test
    public void shouldReturnGreeting() {
        testing
                .givenEvent()
                .withBody("shukawam")
                .enqueue();
        testing.thenRun(ClientFunction.class, "handleRequest");

        FnResult result = testing.getOnlyResult();
        assertEquals("Hello, shukawam!", result.getBodyAsString());
    }

}