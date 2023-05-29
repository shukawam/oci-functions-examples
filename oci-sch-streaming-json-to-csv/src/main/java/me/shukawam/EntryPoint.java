package me.shukawam;

import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fnproject.fn.api.Headers;
import com.fnproject.fn.api.OutputEvent;
import com.fnproject.fn.api.OutputEvent.Status;

/**
 * This Function converts JSON to CSV format.
 * 
 * @author shukawam
 */
public class EntryPoint {
    private static final Logger logger = Logger.getLogger(EntryPoint.class.getName());

    public OutputEvent handleRequest(List<Input> inputs) {
        CsvMapper csvMapper = new CsvMapper();
        // logging input contents.
        inputs.forEach(input -> {
            logger.info(input.toString());
        });
        CsvSchema csvSchema = csvMapper.schemaFor(Input.class).withHeader();
        try {
            var decodedInputs = inputs.stream().map(i -> new Input(i.getStream(), i.getPartition(),
                    base64Decode(i.getKey()), base64Decode(i.getValue()), i.getOffset(), i.getTimestamp())).toList();
            var csvOutput = csvMapper.writer(csvSchema).writeValueAsString(decodedInputs);
            OutputEvent outputEvent = OutputEvent.fromBytes(
                    csvOutput.getBytes(), Status.Success, "text/csv", Headers.emptyHeaders());
            return outputEvent;
        } catch (JsonProcessingException e) {
            // some error processing.
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private String base64Decode(String encoded) {
        if (encoded == null) {
            return encoded;
        } else {
            return new String(Base64.getDecoder().decode(encoded));
        }
    }
}
