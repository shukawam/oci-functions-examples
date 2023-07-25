package me.shukawam.fn;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import com.fnproject.fn.testing.FnResult;
import com.fnproject.fn.testing.FnTestingRule;

public class EntryPointTest {
    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

    @Test
    public void shouldReturnDefaultMessage() {
        testing.givenEvent().enqueue();
        testing.thenRun(EntryPoint.class, "handleRequest");

        FnResult result = testing.getOnlyResult();
        assertEquals("{\"message\":\"Hello world\"}", result.getBodyAsString());
    }

    @Test
    public void shouldReturnCustomMessage() {
        testing.givenEvent().withBody("John").enqueue();
        testing.thenRun(EntryPoint.class, "handleRequest");

        FnResult result = testing.getOnlyResult();
        assertEquals("{\"message\":\"Hello John\"}", result.getBodyAsString());
    }
}
