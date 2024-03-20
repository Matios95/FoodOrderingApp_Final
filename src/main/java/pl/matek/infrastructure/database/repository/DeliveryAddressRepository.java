package pl.matek.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.matek.business.dao.DeliveryAddressDAO;
import pl.matek.domain.DeliveryAddress;
import pl.matek.domain.Place;
import pl.matek.infrastructure.database.entity.DeliveryAddressEntity;
import pl.matek.infrastructure.database.entity.PlaceEntity;
import pl.matek.infrastructure.database.repository.jpa.DeliveryAddressJpaRepository;
import pl.matek.infrastructure.database.repository.mapper.DeliveryAddressEntityMapper;
import pl.matek.infrastructure.database.repository.mapper.PlaceEntityMapper;

import java.util.List;

@Repository
@AllArgsConstructor
public class DeliveryAddressRepository implements DeliveryAddressDAO {

    private final PlaceEntityMapper placeEntityMapper;
    private final DeliveryAddressEntityMapper addressEntityMapper;
    private final DeliveryAddressJpaRepository addressJpaRepository;

    @Override
    public List<DeliveryAddress> findAllDeliveryWithPlace(Place place) {
        PlaceEntity placeEntity = placeEntityMapper.mapToEntity(place);
        return addressJpaRepository.findAllByPlaced(placeEntity).stream()
                .map(addressEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void create(DeliveryAddress deliveryAddress) {
        DeliveryAddressEntity toSave = addressEntityMapper.mapToEntity(deliveryAddress);
        addressJpaRepository.save(toSave);
    }
}
