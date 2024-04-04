package pl.matek.business.dao;

import pl.matek.domain.FoodOrderingRequest;
import pl.matek.domain.Order;
import pl.matek.domain.Product;

import java.util.List;

public interface OrderProcessingDAO {
    List<Order> findByFoodOrderingRequest(FoodOrderingRequest foodOrderingRequest);

    void create(Order order);

    List<Order> findAllOrderWithProduct(Product product);
}
