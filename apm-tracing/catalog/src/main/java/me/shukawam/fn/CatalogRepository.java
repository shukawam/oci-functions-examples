package me.shukawam.fn;

import java.util.List;

/**
 * @author shukawam
 */
public interface CatalogRepository {

    public List<Catalog> getAllCatalog();

    public Catalog getCatalogById(int id);

    public Catalog createCatalog(Catalog catalog);

    public Catalog updateCatalog(Catalog catalog);

    public int deleteCatalog(int id);

}
