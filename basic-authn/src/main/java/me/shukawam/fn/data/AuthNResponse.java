package me.shukawam.fn.data;

import java.util.List;
import java.util.Map;

/**
 * @author shukawam
 */
public class AuthNResponse {
    // required
    private boolean active;

    // optional
    private List<String> scope;
    private String expiresAt;
    private Map<String, Object> context;
    private String wwwAuthenticate;

    public AuthNResponse(boolean active) {
        this.active = active;
    }

    public AuthNResponse(boolean active, List<String> scope, String expiresAt, Map<String, Object> context) {
        this.active = active;
        this.scope = scope;
        this.expiresAt = expiresAt;
        this.context = context;
    }

    public AuthNResponse(boolean active, String wwwAuthenticate) {
        this.active = active;
        this.wwwAuthenticate = wwwAuthenticate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public String getWwwAuthenticate() {
        return wwwAuthenticate;
    }

    public void setWwwAuthenticate(String wwwAuthenticate) {
        this.wwwAuthenticate = wwwAuthenticate;
    }

}
