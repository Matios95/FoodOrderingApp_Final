package pl.matek.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.matek.business.dao.ProductDAO;
import pl.matek.domain.Place;
import pl.matek.domain.Product;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

    private final ProductDAO productDAO;

    @Transactional
    public List<Product> findAllProductWithPlace(Place place) {
        List<Product> allProductWithPlace = productDAO.findAllProductWithPlace(place);
        log.info("Available product with place: [%s]".formatted(allProductWithPlace.size()));
        return allProductWithPlace;
    }

    public void productCreate(Product product) {
        log.debug("Product create: [%s]".formatted(product.getProductCode()));
        productDAO.productCreate(product);
    }

    @Transactional
    public Product findByCode(String productCode) {
        return productDAO.findByCode(productCode);
    }
}
