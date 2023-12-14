package me.shukawam;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnproject.fn.testing.FnTestingRule;

public class EntryPointTest {

    @Rule
    public FnTestingRule testing = FnTestingRule.createDefault();

    @Test
    public void shouldReturnOk() throws JsonProcessingException {
        var mapper = new ObjectMapper();
        var request = new Request("mynewstream", "0", null,
                "VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wZWQgb3ZlciB0aGUgbGF6eSBkb2cu", 0, "2018-04-26T01:03:06.051Z");
        var requests = List.of(request);
        testing.givenEvent().withBody(mapper.writeValueAsString(requests)).enqueue();
        testing.thenRun(EntryPoint.class, "handleRequest");
        var result = testing.getOnlyResult();
        var expected = "ok";
        assertEquals(expected, result.getBodyAsString());
    }
}
