package me.shukawam.fn;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnproject.fn.testing.FnResult;
import com.fnproject.fn.testing.FnTestingRule;

import me.shukawam.fn.data.AuthNRequest;
import me.shukawam.fn.data.AuthNRequestDetail;
import me.shukawam.fn.data.AuthNResponse;

public class EntryPointTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

//     @Test
//     public void shouldReturnCorrectResponseWithNoSQL() throws JsonProcessingException {
//         var input = new AuthNRequest("USER_DEFINED", new AuthNRequestDetail("Basic ZGVmYXVsdDpkZWZhdWx0"));
//         testing
//                 .setConfig("compartment_id",
//                         "ocid1.compartment.oc1..aaaaaaaanjtbllhqxcg67dq7em3vto2mvsbc6pbgk4pw6cx37afzk3tngmoa")
//                 .setConfig("table_id",
//                         "ocid1.nosqltable.oc1.ap-tokyo-1.amaaaaaassl65iqaljn4eeasub32rfuc5xtq2pr7ovzyu6sksz5uu2aimxuq")
//                 .setConfig("FN_APP_NAME", "sandbox")
//                 .setConfig("FN_FN_NAME", "basic-authn")
//                 .setConfig("OCI_TRACE_COLLECTOR_URL", "https://aaaac6ptrolssaaaaaaaaaaewe.apm-agt.ap-tokyo-1.oci.oraclecloud.com/20200101/observations/private-span?dataFormat=zipin&dataFormatVersion=2&dataKey=NDMCBGA6V3BJOKVFQC3SFDYQMX7SOZBP")
//                 .setConfig("OCI_TRACING_ENABLED", "1")
//                 .givenEvent()
//                 .withHeader("content-type", "application/json")
//                 .withBody(mapper.writeValueAsString(input))
//                 .enqueue();
//         testing.thenRun(EntryPoint.class, "handleRequest");
//         FnResult result = testing.getOnlyResult();
//         assertEquals(mapper.writeValueAsString(new AuthNResponse(true)), result.getBodyAsString());
//     }

}
