package pl.matek.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = "deliveryAddressId")
@ToString(of = {"deliveryAddressId", "postcode", "street"})
public class DeliveryAddress {
    Integer deliveryAddressId;
    Place placeDeliveryAddress;
    String postcode;
    String street;
}
