package me.shukawam.fn;

import com.google.gson.Gson;
import com.oracle.bmc.auth.InstancePrincipalsAuthenticationDetailsProvider;
import com.oracle.bmc.functions.FunctionsInvokeClient;
import com.oracle.bmc.functions.requests.InvokeFunctionRequest;
import com.oracle.bmc.functions.responses.InvokeFunctionResponse;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * @author shukawam
 */
public class CatalogClient {
    private static final String CATALOG_FUNCTION_OCID = "ocid1.fnfunc.oc1.ap-tokyo-1.aaaaaaaaj5dpckbpgmh2j77le6h5fk6ommorecvmvajgci2vmhtnddvtozfa";

    public Object invokeCatalogFunction() {
        InstancePrincipalsAuthenticationDetailsProvider provider = InstancePrincipalsAuthenticationDetailsProvider.builder().build();
        FunctionsInvokeClient functionsInvokeClient = FunctionsInvokeClient.builder().build(provider);
        InvokeFunctionRequest request = InvokeFunctionRequest
                .builder()
                .functionId(CATALOG_FUNCTION_OCID)
                .fnIntent(InvokeFunctionRequest.FnIntent.Httprequest)
                .fnInvokeType(InvokeFunctionRequest.FnInvokeType.Sync)
                .build();
        InvokeFunctionResponse response = functionsInvokeClient.invokeFunction(request);

        try {
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(response.getInputStream(), "UTF-8");
            return gson.fromJson(reader, Catalog.class);
        } catch(UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported encoding format.");
        }

    }
}
