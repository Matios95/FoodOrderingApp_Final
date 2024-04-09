package pl.matek.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.domain.FoodOrderingRequest;
import pl.matek.domain.Order;
import pl.matek.domain.Product;
import pl.matek.infrastructure.database.repository.OrderProcessingRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static pl.matek.util.EntityFixtures.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderProcessingRepository orderProcessingDAO;

    @InjectMocks
    private OrderService orderService;

    @Test
    void findByFoodOrderingRequest_returnListOrders() {
        //given
        var listOrders = List.of(order1(), order1().withOrderaCode("ordercode212313"));
        when(orderProcessingDAO.findByFoodOrderingRequest(any(FoodOrderingRequest.class)))
                .thenReturn(listOrders);

        //when
        var result = orderService.findByFoodOrderingRequest(foodOrderingRequest1());

        //then
        assertEquals(result.size(), 2);
        assertEquals(listOrders, result);
    }

    @Test
    void create_returnVoid() {
        //given
        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);

        //when
        orderService.create(order1());

        //then
        verify(orderProcessingDAO).create(captor.capture());
        assertEquals(order1(), captor.getValue());
    }

    @Test
    void findAllOrderWithProduct_returnListOrders() {
        //given
        var listOrders = List.of(order1(), order1().withOrderaCode("ordercode212313"));
        when(orderProcessingDAO.findAllOrderWithProduct(any(Product.class)))
                .thenReturn(listOrders);

        //when
        var result = orderService.findAllOrderWithProduct(product1());

        //then
        assertEquals(result.size(), 2);
        assertEquals(result, listOrders);
    }
}