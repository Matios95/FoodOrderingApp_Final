package pl.matek.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final OwnerService ownerService;
    private final AddressService addressService;

    @Transactional
    public List<Place> findAllPlaceWithOwner(String email) {
        Owner owner = ownerService.findOwner(email);
        return placeDAO.findAllPlaceWithOwner(owner);
    }

    @Transactional
    public void placeCreate(Place place) {
        Address address = addressService.createAddress(place.getAddressPlace());
        placeDAO.createPlace(place.withAddressPlace(address));
    }

    @Transactional
    public Place findById(Integer placeId) {
        Optional<Place> place = placeDAO.findById(placeId);
        if (place.isEmpty()) {
            throw new NotFoundException("Could not find service by placeId: [%s]".formatted(placeId));
        }
        return place.get();
    }

    @Transactional
    public Page<Place> findAllPlaceWithParam(String postcode, String street, Integer pageNo, Integer pageSize, String sortDir, String sortField) {
        Sort sort = sortDir
                .equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return placeDAO.findAllPlaceWithParam(postcode, street, pageable);
    }
}
