package pl.matek.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.matek.business.dao.OrderProcessingDAO;
import pl.matek.domain.FoodOrderingRequest;
import pl.matek.domain.Order;
import pl.matek.domain.Product;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {

    private final OrderProcessingDAO orderProcessingDAO;

    @Transactional
    public List<Order> findByFoodOrderingRequest(FoodOrderingRequest foodOrderingRequest) {
        List<Order> byFoodOrderingRequest = orderProcessingDAO.findByFoodOrderingRequest(foodOrderingRequest);
        log.info("Available order: [%s]".formatted(byFoodOrderingRequest.size()));
        return byFoodOrderingRequest;
    }

    public void create(Order order) {
        orderProcessingDAO.create(order);
    }

    public List<Order> findAllOrderWithProduct(Product product) {
        return orderProcessingDAO.findAllOrderWithProduct(product);
    }
}
