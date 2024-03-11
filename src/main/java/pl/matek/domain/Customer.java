package pl.matek.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "email")
@ToString(of = {"customerId", "email", "name", "surname"})
public class Customer {
    Integer customerId;
    String email;
    String name;
    String surname;
    Address addressCustomer;
    Set<FoodOrderingRequest> foodOrderingRequestEntities;
}
