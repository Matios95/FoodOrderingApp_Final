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
        return productDAO.findAllProductWithPlace(place);
    }

    public void productCreate(Product product) {
        productDAO.productCreate(product);
    }

    @Transactional
    public Product findByCode(String productCode) {
        return productDAO.findByCode(productCode);
    }
}
