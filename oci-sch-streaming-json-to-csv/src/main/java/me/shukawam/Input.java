package me.shukawam;

import java.sql.Timestamp;

public class Input {
    private String stream;
    private String partition;
    private String key;
    private String value;
    private int offset;
    private Timestamp timestamp;

    public Input() {
    }

    public Input(String stream, String partition, String key, String value, int offset, Timestamp timestamp) {
        this.stream = stream;
        this.partition = partition;
        this.key = key;
        this.value = value;
        this.offset = offset;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Input [stream=" + stream + ", partition=" + partition + ", key=" + key + ", value=" + value
                + ", offset=" + offset + ", timestamp=" + timestamp + "]";
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getPartition() {
        return partition;
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
