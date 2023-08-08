package me.shukawam.fn.data;

public class OpenObserveResponseDetail {
    private String name;
    private int successful;
    private int failed;

    public OpenObserveResponseDetail() {
    }

    public OpenObserveResponseDetail(String name, int successful, int failed) {
        this.name = name;
        this.successful = successful;
        this.failed = failed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSuccessful() {
        return successful;
    }

    public void setSuccessful(int successful) {
        this.successful = successful;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

}
