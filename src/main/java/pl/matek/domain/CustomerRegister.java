package pl.matek.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomerRegister {

    String name;
    String surname;
    String country;
    String postcode;
    String street;
    Integer streetNumber;
    String email;
    String password;
}
