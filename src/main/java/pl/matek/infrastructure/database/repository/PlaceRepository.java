package pl.matek.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.matek.business.dao.PlaceDAO;
import pl.matek.domain.Owner;
import pl.matek.domain.Place;
import pl.matek.infrastructure.database.entity.OwnerEntity;
import pl.matek.infrastructure.database.entity.PlaceEntity;
import pl.matek.infrastructure.database.repository.jpa.PlaceJpaRepository;
import pl.matek.infrastructure.database.repository.mapper.OwnerEntityMapper;
import pl.matek.infrastructure.database.repository.mapper.PlaceEntityMapper;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PlaceRepository implements PlaceDAO {

    private final PlaceJpaRepository placeJpaRepository;
    private final PlaceEntityMapper placeEntityMapper;
    private final OwnerEntityMapper ownerEntityMapper;

    @Override
    public List<Place> findAllPlaceWithOwner(Owner owner) {
        OwnerEntity ownerEntity = ownerEntityMapper.mapToEntity(owner);
        return placeJpaRepository.findAllByOwner(ownerEntity).stream()
                .map(placeEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Place createPlace(Place place) {
        PlaceEntity toSave = placeEntityMapper.mapToEntity(place);
        PlaceEntity saved = placeJpaRepository.save(toSave);
        return placeEntityMapper.mapFromEntity(saved);
    }

    @Override
    public Optional<Place> findById(Integer placeId) {
        return placeJpaRepository.findByPlaceId(placeId)
                .map(placeEntityMapper::mapFromEntity);
    }

    @Override
    public List<Place> findAllPlaceWithParam(String postcode, String street) {
        return placeJpaRepository.findAllParams(postcode, street).stream()
                .map(placeEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Page<PlaceEntity> findAll(Pageable pageable) {
        return placeJpaRepository.findAll(pageable);
    }


}
