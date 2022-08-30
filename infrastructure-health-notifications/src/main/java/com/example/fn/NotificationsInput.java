package com.example.fn;

import java.util.List;

/**
 *
 * @author shukawam
 */
public class NotificationsInput {

    public String dedupeKey;
    public String title;
    public String type;
    public String severity;
    public long timestampEpochMillis;
    public String timestamp;
    public List<AlarmMetaData> alarmMetaData;
    public double version;
}
