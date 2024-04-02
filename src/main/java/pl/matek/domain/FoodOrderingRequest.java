package pl.matek.domain;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "foodOrderingRequestCode")
@ToString(of = {"foodOrderingRequestId", "foodOrderingRequestCode", "datetime"})
public class FoodOrderingRequest {
    Integer foodOrderingRequestId;
    String foodOrderingRequestCode;
    OffsetDateTime datetime;
    Customer customer;
    Boolean completed;
    Set<Order> orderEntities;
}
