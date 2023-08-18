package me.shukawam.fn;

import java.util.Base64;
import java.util.logging.Logger;

import com.oracle.bmc.Region;
import com.oracle.bmc.auth.InstancePrincipalsAuthenticationDetailsProvider;
import com.oracle.bmc.auth.ResourcePrincipalAuthenticationDetailsProvider;
import com.oracle.bmc.nosql.NosqlClient;
import com.oracle.bmc.nosql.requests.GetRowRequest;

public class BasicAuthNProvider {
    private static final Logger logger = Logger.getLogger(BasicAuthNProvider.class.getName());

    private static final String DEFAULT_USER = "default";
    private static final String DEFAULT_PASSWORD = "default";

    public boolean basicAuthNWithFixValue(String token) {
        var correctCredential = new String(Base64.getEncoder()
                .encode(new String(String.format("%s:%s", DEFAULT_USER, DEFAULT_PASSWORD)).getBytes()));
        logger.info(String.format("Input token: %s", token));
        logger.info(String.format("correctCredential: %s", correctCredential));
        if (!correctCredential.equals(token)) {
            logger.info("Input token is not correct.");
            return false;
        }
        return true;
    }

    public boolean basicAuthNWithNoSQL(String token, String compartmentId, String tableId) {
        var nosqlClient =
        NosqlClient.builder().region(Region.AP_TOKYO_1).build(ResourcePrincipalAuthenticationDetailsProvider.builder().build());
        // var nosqlClient = NosqlClient.builder().region(Region.AP_TOKYO_1)
        //         .build(InstancePrincipalsAuthenticationDetailsProvider.builder().build());
        var username = new String(Base64.getDecoder().decode(token)).split(":")[0];
        logger.info(username);
        var response = nosqlClient.getRow(
                GetRowRequest.builder().compartmentId(compartmentId).key(String.format("username:%s", username))
                        .tableNameOrId(tableId).build());
        if (response.getRow().getValue() == null) {
            return false;
        }
        var expectedCredential = new String(Base64.getEncoder()
                .encode(new String(String.format("%s:%s", response.getRow().getValue().get("username"),
                        response.getRow().getValue().get("password"))).getBytes()));
        if (!expectedCredential.equals(token)) {
            logger.info("Input token is not correct.");
            return false;
        }
        return true;
    }
}
