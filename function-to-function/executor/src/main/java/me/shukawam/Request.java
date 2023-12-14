package me.shukawam;

public class Request {
    private String stream;
    private String partition;
    private String key;
    private String value;
    private long offset;
    private String timestamp;

    public Request() {
    }

    public Request(String stream, String partition, String key, String value, long offset, String timestamp) {
        this.stream = stream;
        this.partition = partition;
        this.key = key;
        this.value = value;
        this.offset = offset;
        this.timestamp = timestamp;
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

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Request [stream=" + stream + ", partition=" + partition + ", key=" + key + ", value=" + value
                + ", offset=" + offset + ", timestamp=" + timestamp + "]";
    }

}
