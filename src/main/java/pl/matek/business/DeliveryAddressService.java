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
        List<DeliveryAddress> deliveryAddress = deliveryAddressDAO.findAllDeliveryWithPlace(place);
        log.info("Available delivery address with place: [%s]".formatted(deliveryAddress.size()));
        return deliveryAddress;
    }

    @Transactional
    public void deliveryAddressCreate(DeliveryAddress deliveryAddress) {
        log.debug("Delivery address create: [%s]".formatted(deliveryAddress.getPlaceDeliveryAddress()));
        deliveryAddressDAO.create(deliveryAddress);
    }
}
