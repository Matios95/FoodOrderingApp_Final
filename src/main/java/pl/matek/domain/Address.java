package pl.matek.domain;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.With;

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
