package pl.matek.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.domain.Address;
import pl.matek.infrastructure.database.repository.AddressRepository;
import pl.matek.util.EntityFixtures;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pl.matek.util.EntityFixtures.*;


@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressDAO;

    @InjectMocks
    private AddressService addressService;

    @Test
    void createAddress_returnAddress() {
        //give
        when(addressDAO.addressCreate(address1()))
                .thenReturn(address1());

        //when
        Address address = addressService.createAddress(address1());

        //then
        Assertions.assertNotNull(address);
    }
}