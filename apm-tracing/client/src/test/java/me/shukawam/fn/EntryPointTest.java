package me.shukawam.fn;

import com.fnproject.fn.testing.*;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author shukawam
 */
public class EntryPointTest {
    public final FnTestingRule testing = FnTestingRule.createDefault();

    @Test
    public void shouldReturnAllCatalog() {
        testing.givenEvent().enqueue();
        testing.thenRun(EntryPoint.class, "handleRequest");

        FnResult result = testing.getOnlyResult();
        assertEquals("Hello, world!", result.getBodyAsString());
    }
}
