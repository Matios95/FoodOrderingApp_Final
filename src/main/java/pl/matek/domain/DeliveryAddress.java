package pl.matek.domain;

import lombok.*;
import pl.matek.infrastructure.database.entity.PlaceEntity;

@With
@Value
@Builder
@EqualsAndHashCode(of = "deliveryAddressId")
@ToString(of = {"deliveryAddressId", "postcode", "street"})
public class DeliveryAddress {
    Integer deliveryAddressId;
    PlaceEntity placeDeliveryAddress;
    String postcode;
    String street;
}
