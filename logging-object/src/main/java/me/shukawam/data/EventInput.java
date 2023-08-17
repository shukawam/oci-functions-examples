package me.shukawam.data;

public class EventInput {
    private String cloudEventsVersion;
    private String eventID;
    private String eventType;
    private String source;
    private String eventTypeVersion;
    private String eventTime;
    private String contentType;
    private Extensions extensions;
    private Data data;

    public String getCloudEventsVersion() {
        return cloudEventsVersion;
    }

    public void setCloudEventsVersion(String cloudEventsVersion) {
        this.cloudEventsVersion = cloudEventsVersion;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEventTypeVersion() {
        return eventTypeVersion;
    }

    public void setEventTypeVersion(String eventTypeVersion) {
        this.eventTypeVersion = eventTypeVersion;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Extensions getExtensions() {
        return extensions;
    }

    public void setExtensions(Extensions extensions) {
        this.extensions = extensions;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Extensions {
        private String compartmentId;

        public String getCompartmentId() {
            return compartmentId;
        }

        public void setCompartmentId(String compartmentId) {
            this.compartmentId = compartmentId;
        }

    }

    public static class Data {
        private String compartmentId;
        private String compartmentName;
        private String resourceName;
        private String resourceId;
        private String availabilityDomain;
        private AdditionalDetails additionalDetails;

        public String getCompartmentId() {
            return compartmentId;
        }

        public void setCompartmentId(String compartmentId) {
            this.compartmentId = compartmentId;
        }

        public String getCompartmentName() {
            return compartmentName;
        }

        public void setCompartmentName(String compartmentName) {
            this.compartmentName = compartmentName;
        }

        public String getResourceName() {
            return resourceName;
        }

        public void setResourceName(String resourceName) {
            this.resourceName = resourceName;
        }

        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }

        public String getAvailabilityDomain() {
            return availabilityDomain;
        }

        public void setAvailabilityDomain(String availabilityDomain) {
            this.availabilityDomain = availabilityDomain;
        }

        public AdditionalDetails getAdditionalDetails() {
            return additionalDetails;
        }

        public void setAdditionalDetails(AdditionalDetails additionalDetails) {
            this.additionalDetails = additionalDetails;
        }

    }

    public static class AdditionalDetails {
        private String eTag;
        private String namespace;
        private String bucketName;
        private String bucketId;
        private String archivalState;
        private String versionId;

        public String geteTag() {
            return eTag;
        }

        public void seteTag(String eTag) {
            this.eTag = eTag;
        }

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }

        public String getBucketId() {
            return bucketId;
        }

        public void setBucketId(String bucketId) {
            this.bucketId = bucketId;
        }

        public String getArchivalState() {
            return archivalState;
        }

        public void setArchivalState(String archivalState) {
            this.archivalState = archivalState;
        }

        public String getVersionId() {
            return versionId;
        }

        public void setVersionId(String versionId) {
            this.versionId = versionId;
        }

    }
}
