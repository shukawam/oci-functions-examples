package me.shukawam.fn;

import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;

import io.micronaut.http.MediaType;

@Controller("/nosql")
public class NosqlController {
    private final NosqlService nosqlService;

    @Inject
    public NosqlController(NosqlService nosqlService) {
        this.nosqlService = nosqlService;
    }

    @Get("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getNosqlTables() {
        return nosqlService.listTableNames();
    }

    @Post("/insert/{tableName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee insertRow(@Body Employee employee, @PathVariable("tableName") String tableName) {
        return nosqlService.insertRow(tableName, employee);
    }

    @Get("/read/{tableName}/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> readRow(@PathVariable("tableName") String tableName, @PathVariable("key") String key) {
        return nosqlService.readRow(tableName, key);
    }
}
