package pl.matek.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "foodOrderingRequestCode")
@ToString(of = {"foodOrderingRequestId", "foodOrderingRequestCode", "datetime"})
public class FoodOrderingRequest {
    Integer foodOrderingRequestId;
    Integer foodOrderingRequestCode;
    LocalDateTime datetime;
    Customer customer;
    Boolean completed;
    Set<Order> orderEntities;
}
