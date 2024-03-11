package pl.matek.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "phone")
@ToString(of = {"placeId", "phone", "name"})
public class Place {
    Integer placeId;
    String phone;
    String name;
    Owner owner;
    Address addressPlace;
    Set<Product> productEntities;
}
