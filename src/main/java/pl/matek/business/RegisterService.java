package pl.matek.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.matek.domain.*;

@Slf4j
@Service
@AllArgsConstructor
public class RegisterService {

    private final OwnerService ownerService;
    private final UserService userService;
    private final CustomerService customerService;
    private final AddressService addressService;
    final String ROLE_OWNER = "OWNER";
    final String ROLE_CUSTOMER = "CUSTOMER";

    @Transactional
    public void ownerCreate(OwnerRegister ownerRegister) {
        userService.userCreate(ROLE_OWNER, User.builder()
                .email(ownerRegister.getEmail())
                .password(ownerRegister.getPassword())
                .active(true)
                .build());
        ownerService.ownerCreate(Owner.builder()
                .name(ownerRegister.getName())
                .surname(ownerRegister.getSurname())
                .email(ownerRegister.getEmail())
                .build());
        log.debug("Owner is create: [%s]".formatted(ownerRegister.getEmail()));
    }

    @Transactional
    public void customerCreate(CustomerRegister customerRegister) {
        userService.userCreate(ROLE_CUSTOMER, User.builder()
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
        customerService.customerCreate(Customer.builder()
                .email(customerRegister.getEmail())
                .name(customerRegister.getName())
                .surname(customerRegister.getSurname())
                .addressCustomer(address)
                .build());
        log.debug("Customer create: [%s]".formatted(customerRegister.getEmail()));
    }
}
