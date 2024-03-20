package pl.matek.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.matek.business.dao.DeliveryAddressDAO;
import pl.matek.domain.DeliveryAddress;
import pl.matek.domain.Place;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressDAO deliveryAddressDAO;

    @Transactional
    public List<DeliveryAddress> findAllDeliveryWithPlace(Place place) {
        return deliveryAddressDAO.findAllDeliveryWithPlace(place);
    }

    @Transactional
    public void deliveryAddressCreate(DeliveryAddress deliveryAddress) {
        deliveryAddressDAO.create(deliveryAddress);
    }
}
