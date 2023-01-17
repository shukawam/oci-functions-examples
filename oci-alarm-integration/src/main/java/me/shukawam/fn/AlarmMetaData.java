package me.shukawam.fn;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlarmMetaData {
    private String id;
    /** OK, FIRING */
    private String status;
    /** CRITICAL, ERROR, WARNING, INFO */
    private String severity;
    private String query;
    private int totalMetricsFiring;
    private List<Map<String, Object>> dimensions;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
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
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * @return the totalMetricsFiring
     */
    public int getTotalMetricsFiring() {
        return totalMetricsFiring;
    }

    /**
     * @param totalMetricsFiring the totalMetricsFiring to set
     */
    public void setTotalMetricsFiring(int totalMetricsFiring) {
        this.totalMetricsFiring = totalMetricsFiring;
    }

    /**
     * @return the dimensions
     */
    public List<Map<String, Object>> getDimensions() {
        return dimensions;
    }

    /**
     * @param dimensions the dimensions to set
     */
    public void setDimensions(List<Map<String, Object>> dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public String toString() {
        return "AlarmMetaData [id=" + id + ", status=" + status + ", severity=" + severity + ", query=" + query
                + ", totalMetricsFiring=" + totalMetricsFiring + ", dimensions=" + dimensions + "]";
    }

}
