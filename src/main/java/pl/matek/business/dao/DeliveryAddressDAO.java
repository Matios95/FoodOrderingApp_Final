package pl.matek.business.dao;

import pl.matek.domain.DeliveryAddress;
import pl.matek.domain.Place;

import java.util.List;

public interface DeliveryAddressDAO {
    List<DeliveryAddress> findAllDeliveryWithPlace(Place place);

    void create(DeliveryAddress deliveryAddress);
}
