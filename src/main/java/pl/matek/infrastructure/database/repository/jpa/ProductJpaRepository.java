package pl.matek.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.matek.infrastructure.database.entity.PlaceEntity;
import pl.matek.infrastructure.database.entity.ProductEntity;

import java.util.List;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Integer> {

    List<ProductEntity> findAllByPlace(PlaceEntity placeEntity);
}
