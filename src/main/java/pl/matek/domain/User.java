package pl.matek.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {
    String email;
    String password;
    Boolean active;
}
