package pl.matek.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.matek.business.dao.AddressDAO;
import pl.matek.business.dao.PlaceDAO;
import pl.matek.domain.Address;
import pl.matek.domain.Owner;
import pl.matek.domain.Place;
import pl.matek.domain.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PlaceService {

    private final PlaceDAO placeDAO;
    private final AddressDAO addressDAO;
    private final OwnerService ownerService;
    private final AddressService addressService;

    @Transactional
    public List<Place> findAllPlaceWithOwner(String email) {
        Owner owner = ownerService.findOwner(email);
        return placeDAO.findAllPlaceWithOwner(owner);
    }

    @Transactional
    public Place placeCreate(Place place) {
        Address address = addressService.createAddress(place.getAddressPlace());
        return placeDAO.createPlace(place.withAddressPlace(address));
    }

    @Transactional
    public Place findById(Integer placeId) {
        Optional<Place> place = placeDAO.findById(placeId);
        if (place.isEmpty()){
            throw new NotFoundException("Could not find service by placeId: [%s]".formatted(placeId));
        }
        return place.get();
    }
}