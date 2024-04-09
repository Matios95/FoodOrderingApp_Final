package pl.matek.infrastructure.database.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.matek.domain.Owner;
import pl.matek.domain.Place;
import pl.matek.infrastructure.database.entity.OwnerEntity;
import pl.matek.infrastructure.database.entity.PlaceEntity;
import pl.matek.infrastructure.database.repository.jpa.PlaceJpaRepository;
import pl.matek.infrastructure.database.repository.mapper.OwnerEntityMapperImpl;
import pl.matek.infrastructure.database.repository.mapper.PlaceEntityMapperImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static pl.matek.util.EntityFixtures.*;

@ExtendWith(MockitoExtension.class)
class PlaceRepositoryTest {

    @Mock
    private PlaceJpaRepository placeJpaRepository;

    @Mock
    private PlaceEntityMapperImpl placeEntityMapper;

    @Mock
    private OwnerEntityMapperImpl ownerEntityMapper;

    @InjectMocks
    private PlaceRepository placeRepository;

    @Test
    void findAllPlaceWithOwner() {
        //given
        var listEntity = List.of(placeEntity1(), placeEntity2());
        var list = List.of(place1(), place2());
        when(ownerEntityMapper.mapToEntity(any(Owner.class)))
                .thenReturn(ownerEntity1());
        when(placeJpaRepository.findAllByOwner(any(OwnerEntity.class)))
                .thenReturn(listEntity);
        when(placeEntityMapper.mapFromEntity(any(PlaceEntity.class)))
                .thenCallRealMethod();

        //when
        var result = placeRepository.findAllPlaceWithOwner(owner1());

        //then
        assertEquals(result.size(), 2);
        assertEquals(list, result);
    }

    @Test
    void createPlace() {
        //given
        when(placeEntityMapper.mapToEntity(any(Place.class)))
                .thenReturn(placeEntity1());
        when(placeJpaRepository.save(any(PlaceEntity.class)))
                .thenReturn(placeEntity1());
        when(placeEntityMapper.mapFromEntity(any(PlaceEntity.class)))
                .thenCallRealMethod();

        //when
        var result = placeRepository.createPlace(place1());

        //then
        assertNotNull(result);
        assertEquals(place1(), result);
    }

    @Test
    void findById() {
        //given
        when(placeJpaRepository.findByPlaceId(anyInt()))
                .thenReturn(Optional.ofNullable(placeEntity1()));
        when(placeEntityMapper.mapFromEntity(any(PlaceEntity.class)))
                .thenCallRealMethod();

        //when
        var result = placeRepository.findById(placeEntity1().getPlaceId());

        //then
        assertFalse(result.isEmpty());
        assertEquals(place1(), result.get());
    }

    @Test
    void findAllPlaceWithParam() {
        //given
        PageRequest request = PageRequest.of(0, 10);
        when(placeJpaRepository.findAllParams(anyString(), anyString(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(placeEntity1(), placeEntity2()), request, 10));
        when(placeEntityMapper.mapFromEntity(any(PlaceEntity.class)))
                .thenCallRealMethod();

        //when
        var result = placeRepository.findAllPlaceWithParam("11-111", "Street", request);

        //then
        assertEquals(result.getContent().size(), 2);
    }
}