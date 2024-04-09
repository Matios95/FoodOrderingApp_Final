package pl.matek.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.domain.Address;
import pl.matek.domain.Customer;
import pl.matek.domain.Owner;
import pl.matek.domain.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static pl.matek.util.EntityFixtures.customerRegister1;
import static pl.matek.util.EntityFixtures.ownerRegister1;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    @Mock
    private OwnerService ownerService;

    @Mock
    private UserService userService;

    @Mock
    private CustomerService customerService;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private RegisterService registerService;

    @Test
    void ownerCreate_isCompletedMethodCreated() {
        //when
        registerService.ownerCreate(ownerRegister1());

        //then
        verify(userService, times(1)).userCreate(anyString(), any(User.class));
        verify(ownerService, times(1)).ownerCreate(any(Owner.class));
    }

    @Test
    void customerCreate_isCompletedMethodCreated() {
        //given
        Address address = Address.builder()
                .country(customerRegister1().getCountry())
                .postcode(customerRegister1().getPostcode())
                .street(customerRegister1().getStreet())
                .streetNumber(customerRegister1().getStreetNumber())
                .build();
        when(addressService.createAddress(any(Address.class)))
                .thenReturn(address);
        ArgumentCaptor<Address> captor = ArgumentCaptor.forClass(Address.class);

        //when
        registerService.customerCreate(customerRegister1());

        //given
        verify(userService).userCreate(anyString(), any(User.class));
        verify(addressService).createAddress(captor.capture());
        verify(customerService).customerCreate(any(Customer.class));
        assertEquals(address, captor.getValue());
    }
}