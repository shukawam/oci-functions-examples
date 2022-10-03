package me.shukawam.fn;

import java.util.List;

/**
 * @author shukawam
 */
public class EntryPoint {

    public List<Catalog> handleRequest() {
        InMemoryCatalogRepositoryImpl repository = new InMemoryCatalogRepositoryImpl();
        return repository.getAllCatalog();
    }
}
