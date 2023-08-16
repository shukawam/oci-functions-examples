package me.shukawam.fn;

import me.shukawam.fn.data.AuthNRequest;

public class BasicAuthNUtils {
    private BasicAuthNUtils() {
    }

    public static boolean isValidInput(AuthNRequest request) {
        if (request == null) {
            return false;
        }
        if (request.getType() == null) {
            return false;
        }
        if (request.getType().isEmpty()
                || (!"USER_DEFINED".equals(request.getType()) && !"TOKEN".equals(request.getType()))) {
            return false;
        }
        if (request.getData().getToken().isEmpty() || request.getData().getToken() == null) {
            return false;
        }
        return true;
    }

    public static boolean isValidAuthNSchema(String scheme) {
        if (!"Basic".equals(scheme)) {
            return false;
        }
        return true;
    }
}
