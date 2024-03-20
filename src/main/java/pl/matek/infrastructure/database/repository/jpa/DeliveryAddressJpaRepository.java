package pl.matek.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.matek.domain.DeliveryAddress;
import pl.matek.infrastructure.database.entity.AddressEntity;
import pl.matek.infrastructure.database.entity.DeliveryAddressEntity;
import pl.matek.infrastructure.database.entity.PlaceEntity;

import java.util.Arrays;
import java.util.List;

@Repository
public interface DeliveryAddressJpaRepository extends JpaRepository<DeliveryAddressEntity, Integer> {

    List<DeliveryAddressEntity> findAllByPlaced(PlaceEntity placeEntity);
}
