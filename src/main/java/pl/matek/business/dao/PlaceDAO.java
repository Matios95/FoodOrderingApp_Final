package pl.matek.business.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.matek.domain.Owner;
import pl.matek.domain.Place;

import java.util.List;
import java.util.Optional;

public interface PlaceDAO {
    List<Place> findAllPlaceWithOwner(Owner owner);

    Place createPlace(Place place);

    Optional<Place> findById(Integer placeId);

    Page<Place> findAllPlaceWithParam(String postcode, String street, Pageable pageable);

    List<Place> findAll();
}
