package pl.matek.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.matek.business.dao.ProductDAO;
import pl.matek.domain.Place;
import pl.matek.domain.Product;
import pl.matek.infrastructure.database.entity.PlaceEntity;
import pl.matek.infrastructure.database.entity.ProductEntity;
import pl.matek.infrastructure.database.repository.jpa.ProductJpaRepository;
import pl.matek.infrastructure.database.repository.mapper.PlaceEntityMapper;
import pl.matek.infrastructure.database.repository.mapper.ProductEntityMapper;

import java.util.List;

@Repository
@AllArgsConstructor
public class ProductRepository implements ProductDAO {

    private final ProductJpaRepository productJpaRepository;
    private final ProductEntityMapper productEntityMapper;
    private final PlaceEntityMapper placeEntityMapper;

    @Override
    public List<Product> findAllProductWithPlace(Place place) {
        PlaceEntity placeEntity = placeEntityMapper.mapToEntity(place);
        return productJpaRepository.findAllByPlace(placeEntity).stream()
                .map(productEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void productCreate(Product product) {
        ProductEntity toSave = productEntityMapper.mapToEntity(product);
        productJpaRepository.save(toSave);
    }

    @Override
    public Product findByCode(String productCode) {
        ProductEntity productEntity = productJpaRepository.findByProductCode(productCode);
        return productEntityMapper.mapFromEntity(productEntity);
    }
}
