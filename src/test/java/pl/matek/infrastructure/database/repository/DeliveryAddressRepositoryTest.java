package pl.matek.infrastructure.database.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.domain.DeliveryAddress;
import pl.matek.domain.Place;
import pl.matek.infrastructure.database.entity.DeliveryAddressEntity;
import pl.matek.infrastructure.database.entity.PlaceEntity;
import pl.matek.infrastructure.database.repository.jpa.DeliveryAddressJpaRepository;
import pl.matek.infrastructure.database.repository.mapper.DeliveryAddressEntityMapperImpl;
import pl.matek.infrastructure.database.repository.mapper.PlaceEntityMapperImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pl.matek.util.EntityFixtures.*;

@ExtendWith(MockitoExtension.class)
class DeliveryAddressRepositoryTest {

    @Mock
    private PlaceEntityMapperImpl placeEntityMapper;

    @Mock
    private DeliveryAddressEntityMapperImpl addressEntityMapper;

    @Mock
    private DeliveryAddressJpaRepository addressJpaRepository;

    @InjectMocks
    private DeliveryAddressRepository addressRepository;

    @Test
    void findAllDeliveryWithPlace() {
        //given
        List<DeliveryAddress> deliveryAddresses = List.of(deliveryAddress1(), deliveryAddress2());
        List<DeliveryAddressEntity> deliveryAddressEntities = List.of(deliveryAddressEntity1(), deliveryAddressEntity2());
        Mockito.when(placeEntityMapper.mapToEntity(Mockito.any(Place.class)))
                .thenCallRealMethod();
        Mockito.when(addressJpaRepository.findAllByPlaced(Mockito.any(PlaceEntity.class)))
                .thenReturn(deliveryAddressEntities);
        Mockito.when(addressEntityMapper.mapFromEntity(Mockito.any(DeliveryAddressEntity.class)))
                .thenCallRealMethod();

        //when
        List<DeliveryAddress> result = addressRepository.findAllDeliveryWithPlace(place1());

        //then
        assertEquals(result.size(), 2);
        assertEquals(deliveryAddresses, result);
    }

    @Test
    void create() {
        //give
        var method = mock(DeliveryAddressRepository.class);

        //when
        ArgumentCaptor<DeliveryAddress> captor = ArgumentCaptor.forClass(DeliveryAddress.class);
        doNothing().when(method).create(captor.capture());
        method.create(deliveryAddress1());

        //then
        assertEquals(deliveryAddress1(), captor.getValue());
    }
}