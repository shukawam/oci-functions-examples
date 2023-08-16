package me.shukawam.fn.data;

/**
 * @author shukawam
 */
public class AuthNRequestDetail {
    private String token;

    public AuthNRequestDetail() {
    }

    public AuthNRequestDetail(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
