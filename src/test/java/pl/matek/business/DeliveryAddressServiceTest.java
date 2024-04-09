package pl.matek.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.domain.DeliveryAddress;
import pl.matek.infrastructure.database.repository.DeliveryAddressRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.matek.util.EntityFixtures.*;

@ExtendWith(MockitoExtension.class)
class DeliveryAddressServiceTest {

    @Mock
    private DeliveryAddressRepository deliveryAddressDAO;

    @InjectMocks
    private DeliveryAddressService addressService;

    @Test
    void findAllDeliveryWithPlace_returnListDeliveryAddress() {
        //given
        var list = List.of(deliveryAddress1(), deliveryAddress2());
        when(deliveryAddressDAO.findAllDeliveryWithPlace(place1()))
                .thenReturn(list);

        //when
        var result = addressService.findAllDeliveryWithPlace(place1());

        //then
        assertEquals(result.size(), 2);
    }

    @Test
    void deliveryAddressCreate_returnVoid() {
        //given
        ArgumentCaptor<DeliveryAddress> captor = ArgumentCaptor.forClass(DeliveryAddress.class);

        //when
        addressService.deliveryAddressCreate(deliveryAddress1());

        // then
        verify(deliveryAddressDAO).create(captor.capture());
        assertEquals(deliveryAddress1(), captor.getValue());
    }
}