package pl.matek.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.matek.domain.*;

@Service
@AllArgsConstructor
public class RegisterService {

    private final OwnerService ownerService;
    private final UserService userService;
    private final CustomerService customerService;
    private final AddressService addressService;
    private final String ROLE_OWNER = "OWNER";
    private final String ROLE_CUSTOMER = "CUSTOMER";

    @Transactional
    public void ownerCreate(OwnerRegister ownerRegister) {
        User user = userService.userCreate(ROLE_OWNER, User.builder()
                .email(ownerRegister.getEmail())
                .password(ownerRegister.getPassword())
                .active(true)
                .build());
        Owner owner = ownerService.ownerCreate(Owner.builder()
                .name(ownerRegister.getName())
                .surname(ownerRegister.getSurname())
                .email(ownerRegister.getEmail())
                .build());
    }

    @Transactional
    public void customerCreate(CustomerRegister customerRegister) {
        User user = userService.userCreate(ROLE_CUSTOMER, User.builder()
                .email(customerRegister.getEmail())
                .password(customerRegister.getPassword())
                .active(true)
                .build());
        Address address = addressService.createAddress(Address.builder()
                .country(customerRegister.getCountry())
                .postcode(customerRegister.getPostcode())
                .street(customerRegister.getStreet())
                .streetNumber(customerRegister.getStreetNumber())
                .build());
        Customer customer = customerService.customerCreate(Customer.builder()
                .email(customerRegister.getEmail())
                .name(customerRegister.getName())
                .surname(customerRegister.getSurname())
                .addressCustomer(address)
                .build());
    }
}
