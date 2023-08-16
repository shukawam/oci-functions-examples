package me.shukawam.fn;

import me.shukawam.fn.data.AuthNRequest;
import me.shukawam.fn.data.AuthNResponse;

public class SimpleEntryPoint {

    public AuthNResponse handleRequest(AuthNRequest request) {
        if (!BasicAuthNUtils.isValidInput(request)) {
            return new AuthNResponse(false, "Basic realm=\"Input format is invalid.\"");
        }
        var scheme = request.getData().getToken().split(" ")[0];
        var token = request.getData().getToken().split(" ")[1];
        if (!BasicAuthNUtils.isValidAuthNSchema(scheme)) {
            return new AuthNResponse(false, "Basic realm=\"Schema is invalid.\"");
        }
        if (!new BasicAuthNProvider().basicAuthNWithFixValue(token)) {
            return new AuthNResponse(false, "Basic realm=\"Username or Password is invalid.\"");
        }
        return new AuthNResponse(true);
    }
}
