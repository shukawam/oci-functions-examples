package me.shukawam.fn;

import java.util.logging.Logger;

public class EntryPoint {
    private static final Logger logger = Logger.getLogger(EntryPoint.class.getName());

    public String handleRequest(AlarmMessage alarmMessage) {
        logger.info(alarmMessage.toString());
        return "ack.";
    }
}
