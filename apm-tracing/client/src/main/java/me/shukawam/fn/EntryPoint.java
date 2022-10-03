package me.shukawam.fn;

/**
 * @author shukawam
 */
public class EntryPoint {

    public Object handleRequest() {
        CatalogClient client = new CatalogClient();
        return client.invokeCatalogFunction();
    }
}
