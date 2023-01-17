package me.shukawam.fn;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import com.fnproject.fn.testing.FnTestingRule;

public class EntryPointTest {

    @Rule
    public FnTestingRule test = FnTestingRule.createDefault();

    @Test
    public void shouldReturnMessage() {
        String inputJson = """
                {
                    "dedupeKey": "exampleuniqueID",
                    "title": "High CPU Utilization",
                    "body": "Follow runbook at http://example.com/runbooks",
                    "type": "REPEAT",
                    "severity": "CRITICAL",
                    "timestampEpochMillis": 1542406320000,
                    "timestamp": "2018-11-16T22:12:00Z",
                    "alarmMetaData": [
                      {
                        "id": "ocid1.alarm.oc1.iad.exampleuniqueID",
                        "status": "FIRING",
                        "severity": "CRITICAL",
                        "query": "CpuUtilization[1m].mean() > 0",
                        "totalMetricsFiring": 2,
                        "dimensions": [
                          {
                            "instancePoolId": "Default",
                            "resourceDisplayName": "myinstance1",
                            "faultDomain": "FAULT-DOMAIN-1",
                            "resourceId": "ocid1.instance.oc1.iad.exampleuniqueID",
                            "imageId": "ocid1.image.oc1.iad.exampleuniqueID",
                            "availabilityDomain": "szYB:US-ASHBURN-AD-1",
                            "shape": "VM.Standard2.1",
                            "region": "us-ashburn-1"
                          },
                          {
                            "instancePoolId": "Default",
                            "resourceDisplayName": "myinstance2",
                            "faultDomain": "FAULT-DOMAIN-3",
                            "resourceId": "ocid1.instance.oc1.iad.exampleuniqueID",
                            "imageId": "ocid1.image.oc1.iad.exampleuniqueID",
                            "availabilityDomain": "szYB:US-ASHBURN-AD-1",
                            "shape": "VM.Standard2.1",
                            "region": "us-ashburn-1"
                          }
                        ]
                      }
                    ],
                    "version": 1.1
                  }
                    """;
        test.givenEvent().withBody(inputJson).enqueue().thenRun(EntryPoint.class, "handleRequest");
        assertEquals("ack.", test.getOnlyResult().getBodyAsString());
    }
}
