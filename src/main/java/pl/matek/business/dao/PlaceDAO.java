package pl.matek.business.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.matek.domain.Owner;
import pl.matek.domain.Place;
import pl.matek.infrastructure.database.entity.PlaceEntity;

import java.util.List;
import java.util.Optional;

public interface PlaceDAO {
    List<Place> findAllPlaceWithOwner(Owner owner);

    Place createPlace(Place place);

    Optional<Place> findById(Integer placeId);

    List<Place> findAllPlaceWithParam(String postcode, String street);

    Page<PlaceEntity> findAll(Pageable pageable);
}
