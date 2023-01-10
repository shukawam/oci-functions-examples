package me.shukawam.fn;

import java.util.List;
import java.util.Map;

import com.oracle.bmc.auth.InstancePrincipalsAuthenticationDetailsProvider;
import com.oracle.bmc.nosql.NosqlClient;
import com.oracle.bmc.nosql.model.UpdateRowDetails;
import com.oracle.bmc.nosql.requests.GetRowRequest;
import com.oracle.bmc.nosql.requests.ListTablesRequest;
import com.oracle.bmc.nosql.requests.UpdateRowRequest;
import com.oracle.bmc.nosql.responses.GetRowResponse;
import com.oracle.bmc.nosql.responses.UpdateRowResponse;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

@Singleton
public class NosqlService {
    private final InstancePrincipalsAuthenticationDetailsProvider provider;
    private final NosqlClient nosqlClient;
    @Value("${oci.compartmentId}")
    private String compartmentId;

    public NosqlService() {
        provider = InstancePrincipalsAuthenticationDetailsProvider.builder().build();
        nosqlClient = NosqlClient.builder().build(provider);
    }

    public List<String> listTableNames() {
        return nosqlClient.listTables(
                ListTablesRequest.builder().compartmentId(compartmentId).build()).getTableCollection().getItems()
                .stream().map(table -> table.getName()).toList();
    }

    public Employee insertRow(String tableName, Employee employee) {
        nosqlClient.updateRow(
                UpdateRowRequest.builder().tableNameOrId(tableName).updateRowDetails(
                        UpdateRowDetails.builder().compartmentId(compartmentId)
                                .value(Map.of("id", employee.getId(), "details", employee)).build())
                        .build());
        return employee;
    }

    public Map<String, Object> readRow(String tableName, String key) {
        GetRowResponse response =  nosqlClient.getRow(
            GetRowRequest.builder().compartmentId(compartmentId).tableNameOrId(tableName).key(String.format("id:%s", key)).build()
        );
        return response.getRow().getValue();
    }

}
