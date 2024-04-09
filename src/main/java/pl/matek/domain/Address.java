package pl.matek.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"addressId", "country", "postcode", "street", "streetNumber"})
@ToString(of = {"addressId", "country", "postcode", "street", "streetNumber"})
public class Address {
    Integer addressId;
    String country;
    String postcode;
    String street;
    Integer streetNumber;
    Set<Place> placeEntities;
    Set<Customer> customerEntities;
}
