package pl.matek.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OwnerRegister {
    String name;
    String surname;
    String email;
    String password;
}
