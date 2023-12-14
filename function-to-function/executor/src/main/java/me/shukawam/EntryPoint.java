package me.shukawam;

import java.util.List;
import java.util.logging.Logger;

public class EntryPoint {
    private static final Logger LOGGER = Logger.getLogger(EntryPoint.class.getName());

    public String handleRequest(List<Request> requests) {
        requests.forEach(r -> {
            LOGGER.info(String.format("input: %s", r));
        });
        return "ok";
    }
}
