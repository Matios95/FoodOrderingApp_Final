package pl.matek.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@ToString(of = {"addressId", "country", "postcode", "streetNumber"})
public class Address {
    Integer addressId;
    String country;
    String postcode;
    String street;
    Integer streetNumber;
    Set<Place> placeEntities;
    Set<Customer> customerEntities;
}
