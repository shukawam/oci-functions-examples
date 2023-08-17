package me.shukawam;

import java.util.logging.Logger;

import com.fnproject.fn.api.FnConfiguration;
import com.fnproject.fn.api.RuntimeContext;
import com.oracle.bmc.auth.ResourcePrincipalAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;

import me.shukawam.data.EventInput;

public class EntryPoint {

    private ObjectStorageClient objectStorageClient;
    private static final Logger logger = Logger.getLogger(EntryPoint.class.getName());

    @FnConfiguration
    public void config(RuntimeContext ctx) {
        objectStorageClient = ObjectStorageClient
                .builder()
                .build(ResourcePrincipalAuthenticationDetailsProvider.builder().build());
    }

    public String handleRequest(EventInput input) {
        var getObjectRequest = GetObjectRequest
                .builder()
                .namespaceName(input.getData().getAdditionalDetails().getNamespace())
                .bucketName(input.getData().getAdditionalDetails().getBucketName())
                .objectName(input.getData().getResourceName())
                .build();
        var getObjectResponse = objectStorageClient.getObject(getObjectRequest);
        logger.info(getObjectResponse.toString());
        return "ok";
    }
}
