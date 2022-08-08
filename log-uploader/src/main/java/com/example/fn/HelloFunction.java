package com.example.fn;

import com.example.fn.data.OciLogging;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HelloFunction {

    public String handleRequest(OciLogging log) {
        System.out.println(log.logContent.data.message);
        return "ok";
    }

    private SSLContext insecureCtx() {
        TrustManager[] noopManagers = new TrustManager[]{
            new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs, String string) {

                }

                public void checkServerTrusted(X509Certificate[] xcs, String string) {

                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }
        };
        try {
            SSLContext sc = SSLContext.getInstance("ssl");
            sc.init(null, noopManagers, null);
            return sc;
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            return null;
        }
    }

}
