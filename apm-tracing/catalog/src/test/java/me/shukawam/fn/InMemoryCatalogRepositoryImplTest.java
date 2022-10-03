package me.shukawam.fn;

import com.fnproject.fn.testing.FnResult;
import com.fnproject.fn.testing.FnTestingRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author shukawam
 */
public class InMemoryCatalogRepositoryImplTest {
    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

    @Test
    public void shouldReturnAllCatalog() {
        testing.givenEvent().enqueue();
        testing.thenRun(EntryPoint.class, "handleRequest");

        FnResult result = testing.getOnlyResult();
        var expected = "[{\"id\":1,\"name\":\"book\"},{\"id\":2,\"name\":\"pc\"},{\"id\":3,\"name\":\"drink\"},{\"id\":4,\"name\":\"phone\"}]";
        assertEquals(expected, result.getBodyAsString());
    }
}
