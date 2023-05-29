package me.shukawam;

import static org.junit.Assert.assertEquals;

import java.util.logging.Logger;

import org.junit.Rule;
import org.junit.Test;

import com.fnproject.fn.testing.FnTestingRule;

public class EntryPointTest {
    private static final Logger logger = Logger.getLogger(EntryPointTest.class.getName());

    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

    @Test
    public void testConvertJsonToCsv() {
        var input = """
                [
                    {"stream": "mynewstream","partition": "0","key": null,"value": "eyJuYW1lIjoiaWdvciIsICJsb2NhdGlvbiI6ICJEdWJsaW4ifQ==","offset": 0,"timestamp": "2018-04-26T01:03:06.051Z"},
                    {"stream": "mynewstream","partition": "0","key": null,"value": "eyJuYW1lIjoiYnJpYW4iLCJmaWVsZDEiOiJ0ZXN0In0=","offset": 0,"timestamp": "2018-04-26T01:03:06.051Z"}
                ]
                        """;
        logger.info(input);
        testing.givenEvent().withBody(input).enqueue();
        testing.thenRun(EntryPoint.class, "handleRequest");
        var result = testing.getOnlyResult();
        var expectedOutput = """
                key,offset,partition,stream,timestamp,value
                ,0,0,mynewstream,1524704586051,"{""name"":""igor"", ""location"": ""Dublin""}"
                ,0,0,mynewstream,1524704586051,"{""name"":""brian"",""field1"":""test""}"
                    """;
        logger.info(result.getBodyAsString());
        assertEquals(expectedOutput, result.getBodyAsString());
    }
}
