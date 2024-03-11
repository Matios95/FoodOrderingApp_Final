package pl.matek.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "email")
@ToString(of = {"ownerId", "email", "name", "surname"})
public class Owner {
    Integer ownerId;
    String email;
    String name;
    String surname;
    Set<Place> placeEntities;
}
