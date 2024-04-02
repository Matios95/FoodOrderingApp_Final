package pl.matek.business.dao;

import pl.matek.domain.Place;
import pl.matek.domain.Product;

import java.util.List;

public interface ProductDAO {
    List<Product> findAllProductWithPlace(Place place);

    void productCreate(Product product);

    Product findByCode(String productCode);
}
