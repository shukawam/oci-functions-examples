package me.shukawam.fn;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlarmMessage {
    private String dedupekey;
    private String title;
    private String body;
    /** CRITICAL, ERROR, WARNING, INFO */
    private String severity;
    private long timestampEpochMillis;
    private String timestamp;
    private List<AlarmMetaData> alarmMetaData;
    private int version;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */

    /**
     * @return the dedupekey
     */
    public String getDedupekey() {
        return dedupekey;
    }

    /**
     * @param dedupekey the dedupekey to set
     */
    public void setDedupekey(String dedupekey) {
        this.dedupekey = dedupekey;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return the severity
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * @param severity the severity to set
     */
    public void setSeverity(String severity) {
        this.severity = severity;
    }

    /**
     * @return the timestampEpochMillis
     */
    public long getTimestampEpochMillis() {
        return timestampEpochMillis;
    }

    /**
     * @param timestampEpochMillis the timestampEpochMillis to set
     */
    public void setTimestampEpochMillis(long timestampEpochMillis) {
        this.timestampEpochMillis = timestampEpochMillis;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the alarmMetaData
     */
    public List<AlarmMetaData> getAlarmMetaData() {
        return alarmMetaData;
    }

    /**
     * @param alarmMetaData the alarmMetaData to set
     */
    public void setAlarmMetaData(List<AlarmMetaData> alarmMetaData) {
        this.alarmMetaData = alarmMetaData;
    }

    /**
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "AlarmMessage [dedupekey=" + dedupekey + ", title=" + title + ", body=" + body + ", severity=" + severity
                + ", timestampEpochMillis=" + timestampEpochMillis + ", timestamp=" + timestamp + ", alarmMetaData="
                + alarmMetaData + ", version=" + version + "]";
    }

}
