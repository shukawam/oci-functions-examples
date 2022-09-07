package com.example.fn;

import com.oracle.bmc.aivision.AIServiceVisionClient;
import com.oracle.bmc.aivision.model.AnalyzeImageDetails;
import com.oracle.bmc.aivision.model.ImageClassificationFeature;
import com.oracle.bmc.aivision.model.ImageFeature;
import com.oracle.bmc.aivision.model.ObjectStorageImageDetails;
import com.oracle.bmc.aivision.requests.AnalyzeImageRequest;
import com.oracle.bmc.auth.InstancePrincipalsAuthenticationDetailsProvider;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class HelloFunction {

    private static final String COMPARTMENT_ID = "ocid1.compartment.oc1..aaaaaaaanjtbllhqxcg67dq7em3vto2mvsbc6pbgk4pw6cx37afzk3tngmoa";
    private static final Logger LOGGER = Logger.getLogger(HelloFunction.class.getName());

    public String handleRequest(Input input) {
        LOGGER.info(String.format("namespace: %s", input.data.additionalDetails.namespace));
        LOGGER.info(String.format("bucketName: %s", input.data.additionalDetails.bucketName));
        LOGGER.info(String.format("resourceName: %s", input.data.resourceName));
        LOGGER.info(String.format("compartmentId %s", COMPARTMENT_ID));
        var provider = InstancePrincipalsAuthenticationDetailsProvider.builder().build();
        var aiServiceVisionClient = AIServiceVisionClient.builder().build(provider);
        List<ImageFeature> features = Arrays.asList(ImageClassificationFeature.builder().build());
        var objectStorageImageDetails = ObjectStorageImageDetails
            .builder()
            .namespaceName(input.data.additionalDetails.namespace)
            .bucketName(input.data.additionalDetails.bucketName)
            .objectName(input.data.resourceName)
            .build();
        var analyzeImageDetails = AnalyzeImageDetails
            .builder()
            .compartmentId(COMPARTMENT_ID)
            .features(features)
            .image(objectStorageImageDetails)
            .build();
        var analyzeImageResponse = aiServiceVisionClient
            .analyzeImage(AnalyzeImageRequest.builder().analyzeImageDetails(analyzeImageDetails).build());
        return "ok";
    }

}
