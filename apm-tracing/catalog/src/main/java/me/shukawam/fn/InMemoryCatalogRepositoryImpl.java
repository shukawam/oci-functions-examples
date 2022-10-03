package me.shukawam.fn;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author shukawam
 */
public class InMemoryCatalogRepositoryImpl implements CatalogRepository {
    private static final Logger logger = Logger.getLogger(InMemoryCatalogRepositoryImpl.class.getName());

    private final List<Catalog> catalogList;

    public InMemoryCatalogRepositoryImpl() {
        var catalogList = Arrays.asList(
                new Catalog(1, "book"),
                new Catalog(2, "pc"),
                new Catalog(3, "drink"),
                new Catalog(4, "phone")
        );
        this.catalogList = catalogList;
    }

    @Override
    public List<Catalog> getAllCatalog() {
        return catalogList;
    }

    @Override
    public Catalog getCatalogById(int id) {
        return catalogList.get(id + 1);
    }

    @Override
    public Catalog createCatalog(Catalog catalog) {
        logger.info("yet implemented");
        return null;
    }

    @Override
    public Catalog updateCatalog(Catalog catalog) {
        logger.info("yet implemented");
        return null;
    }

    @Override
    public int deleteCatalog(int id) {
        logger.info("yet implemented");
        return 0;
    }
}
