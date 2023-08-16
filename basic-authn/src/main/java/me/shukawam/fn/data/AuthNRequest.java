package me.shukawam.fn.data;

/**
 * @author shukawam
 */
public class AuthNRequest {
    private String type;
    private AuthNRequestDetail data;

    public AuthNRequest() {
    }

    public AuthNRequest(String type, AuthNRequestDetail data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AuthNRequestDetail getData() {
        return data;
    }

    public void setData(AuthNRequestDetail data) {
        this.data = data;
    }

}
