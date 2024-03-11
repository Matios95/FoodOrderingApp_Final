package pl.matek.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = "orderaCode")
@ToString(of = {"orderaId", "orderaCode", "quantity"})
public class Order {
    Integer orderaId;
    Integer orderaCode;
    Integer quantity;
    FoodOrderingRequest foodOrderingRequestEntity;
    Product product;
}
