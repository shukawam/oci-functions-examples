package me.shukawam.fn.data;

import java.util.List;

public class OpenObserveResponse {

    private int code;
    private List<OpenObserveResponseDetail> status;

    public OpenObserveResponse() {
    }

    public OpenObserveResponse(int code, List<OpenObserveResponseDetail> status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<OpenObserveResponseDetail> getStatus() {
        return status;
    }

    public void setStatus(List<OpenObserveResponseDetail> status) {
        this.status = status;
    }

}
