package com.example.fn;

import java.util.List;

/**
 *
 * @author shukawam
 */
public class AlarmMetaData {

    public String id;
    public String status;
    public String severity;
    public String namespace;
    public String query;
    public long totalMetricsFiring;
    public List<Dimension> dimensions;
    public String alarmUrl;
}
