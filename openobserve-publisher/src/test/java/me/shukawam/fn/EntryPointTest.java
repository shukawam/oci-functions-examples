package me.shukawam.fn;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import com.fnproject.fn.testing.FnResult;
import com.fnproject.fn.testing.FnTestingRule;

public class EntryPointTest {
    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

    @Test
    public void shouldReturnOk() {
        var loggingInput = """
                [
                  {
                    "id": "75727e83-831a-4a92-8cf3-5204efc66b9e",
                    "time": "2023-08-07T15:59:24.292Z",
                    "oracle": {
                      "compartmentid": "ocid1.compartment.oc1..aaaaaaaanjtbllhqxcg67dq7em3vto2mvsbc6pbgk4pw6cx37afzk3tngmoa",
                      "ingestedtime": "2023-08-07T15:59:25.684031044Z",
                      "instanceid": "ocid1.instance.oc1.phx.anyhqljrssl65iqcv2akiishofrfa56aibazwksbnvzkhnddpzmtb3ql54ra",
                      "loggroupid": "ocid1.loggroup.oc1.phx.amaaaaaassl65iqaok6fkpe6mb3rut2bqajzul7evjqio65uqwfu2zbouuia",
                      "logid": "ocid1.log.oc1.phx.amaaaaaassl65iqa3jh7um25ej2ay2dr7ymqadwiat3sybrdsafogermi7oq",
                      "tenantid": "ocid1.tenancy.oc1..aaaaaaaa3mb7wrcy2ls3u3jsy2soq5ck3lc3q4mczitpdaymbuazc5tkguca"
                    },
                    "source": "oke-cnlvod5ah4a-nr2e27fkaka-sjatw3dne5a-1",
                    "specversion": "1.0",
                    "subject": "/var/log/containers/eventing-webhook-5cf7766997-tdv7k_knative-eventing_eventing-webhook-807e46f925f0300c2699339ab553b6b7950e01b948d9f6c530e7fb6989c8fbc3.log",
                    "type": "com.oraclecloud.logging.custom.WorkerNodeLoggingPath",
                    "data": {
                      "admissionreview/allowed": true,
                      "admissionreview/result": "nil",
                      "admissionreview/uid": "df858fda-11f4-4108-8b07-5fefbdfbb6db",
                      "caller": "webhook/admission.go:151",
                      "commit": "034bec9",
                      "knative.dev/kind": "/v1, Kind=ConfigMap",
                      "knative.dev/name": "chaos-mesh",
                      "knative.dev/namespace": "chaos-mesh",
                      "knative.dev/operation": "UPDATE",
                      "knative.dev/pod": "eventing-webhook-5cf7766997-tdv7k",
                      "knative.dev/resource": "/v1, Resource=configmaps",
                      "knative.dev/subresource": "",
                      "knative.dev/userinfo": "system:serviceaccount:chaos-mesh:chaos-controller-manager",
                      "level": "info",
                      "logger": "eventing-webhook",
                      "logtag": "F",
                      "msg": "remote admission controller audit annotations=map[string]string(nil)",
                      "stream": "stdout",
                      "tailed_path": "/var/log/containers/eventing-webhook-5cf7766997-tdv7k_knative-eventing_eventing-webhook-807e46f925f0300c2699339ab553b6b7950e01b948d9f6c530e7fb6989c8fbc3.log",
                      "ts": "2023-08-07T15:59:23.449Z"
                    }
                  },{
                      "id": "75727e83-831a-4a92-8cf3-5204efc66b9g",
                      "time": "2023-08-07T15:59:24.292Z",
                      "oracle": {
                        "compartmentid": "ocid1.compartment.oc1..aaaaaaaanjtbllhqxcg67dq7em3vto2mvsbc6pbgk4pw6cx37afzk3tngmoa",
                        "ingestedtime": "2023-08-07T15:59:25.684031044Z",
                        "instanceid": "ocid1.instance.oc1.phx.anyhqljrssl65iqcv2akiishofrfa56aibazwksbnvzkhnddpzmtb3ql54ra",
                        "loggroupid": "ocid1.loggroup.oc1.phx.amaaaaaassl65iqaok6fkpe6mb3rut2bqajzul7evjqio65uqwfu2zbouuia",
                        "logid": "ocid1.log.oc1.phx.amaaaaaassl65iqa3jh7um25ej2ay2dr7ymqadwiat3sybrdsafogermi7oq",
                        "tenantid": "ocid1.tenancy.oc1..aaaaaaaa3mb7wrcy2ls3u3jsy2soq5ck3lc3q4mczitpdaymbuazc5tkguca"
                      },
                      "source": "oke-cnlvod5ah4a-nr2e27fkaka-sjatw3dne5a-1",
                      "specversion": "1.0",
                      "subject": "/var/log/containers/eventing-webhook-5cf7766997-tdv7k_knative-eventing_eventing-webhook-807e46f925f0300c2699339ab553b6b7950e01b948d9f6c530e7fb6989c8fbc3.log",
                      "type": "com.oraclecloud.logging.custom.WorkerNodeLoggingPath",
                      "data": {
                        "admissionreview/allowed": true,
                        "admissionreview/result": "nil",
                        "admissionreview/uid": "df858fda-11f4-4108-8b07-5fefbdfbb6db",
                        "caller": "webhook/admission.go:151",
                        "commit": "034bec9",
                        "knative.dev/kind": "/v1, Kind=ConfigMap",
                        "knative.dev/name": "chaos-mesh",
                        "knative.dev/namespace": "chaos-mesh",
                        "knative.dev/operation": "UPDATE",
                        "knative.dev/pod": "eventing-webhook-5cf7766997-tdv7k",
                        "knative.dev/resource": "/v1, Resource=configmaps",
                        "knative.dev/subresource": "",
                        "knative.dev/userinfo": "system:serviceaccount:chaos-mesh:chaos-controller-manager",
                        "level": "info",
                        "logger": "eventing-webhook",
                        "logtag": "F",
                        "msg": "remote admission controller audit annotations=map[string]string(nil)",
                        "stream": "stdout",
                        "tailed_path": "/var/log/containers/eventing-webhook-5cf7766997-tdv7k_knative-eventing_eventing-webhook-807e46f925f0300c2699339ab553b6b7950e01b948d9f6c530e7fb6989c8fbc3.log",
                        "ts": "2023-08-07T15:59:23.449Z"
                      }
                    }
                  ]
                    """;
        testing.setConfig("OPENOBSERVE_ENDPOINT", "https://openobserve.shukawam.me")
                .setConfig("USERNAME", "shukawam@gmail.com").setConfig("PASSWORD", "SPlwz7kmpwcS3lVn").givenEvent()
                .withBody(loggingInput).enqueue();
        testing.thenRun(EntryPoint.class, "handleRequest");

        FnResult result = testing.getOnlyResult();
        assertEquals("{\"code\":200,\"status\":[{\"name\":\"default\",\"successful\":2,\"failed\":0}]}",
                result.getBodyAsString());
    }
}
