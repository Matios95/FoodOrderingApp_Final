package pl.matek.domain;

import lombok.*;
import pl.matek.infrastructure.database.entity.ProductType;

import java.math.BigDecimal;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "productCode")
@ToString(of = {"productId", "productCode", "type", "name", "description", "price"})
public class Product {
    Integer productId;
    String productCode;
    ProductType type;
    String name;
    String description;
    BigDecimal price;
    Set<Order> orderEntities;
    Place place;
    byte[] image;
}
