package pl.matek.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.domain.*;
import pl.matek.domain.exception.NotFoundException;
import pl.matek.infrastructure.database.entity.ProductType;
import pl.matek.infrastructure.database.repository.FoodOrderingRequestRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static pl.matek.util.EntityFixtures.*;

@ExtendWith(MockitoExtension.class)
class FoodOrderingRequestServiceTest {

    @Mock
    private FoodOrderingRequestRepository foodOrderingRequestDAO;

    @Mock
    private OrderService orderService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private FoodOrderingRequestService foodOrderingRequestService;

    @Test
    void findAllWithCustomer_returnListFoodOrderingRequest() {
        //given
        var list = List.of(foodOrderingRequest1(), foodOrderingRequest2());
        when(foodOrderingRequestDAO.findAllWithCustomer(any(Customer.class)))
                .thenReturn(list);

        //when
        var result = foodOrderingRequestService.findAllWithCustomer(customer1());

        //then
        assertEquals(result.size(), 2);
    }

    @Test
    void findById_returnFoodOrderingRequest() {
        //given
        when(foodOrderingRequestDAO.findById(anyInt()))
                .thenReturn(Optional.ofNullable(foodOrderingRequest1()));

        //when
        var result = foodOrderingRequestService.findById(foodOrderingRequest1().getFoodOrderingRequestId());

        //then
        assertEquals(foodOrderingRequest1(), result);
    }

    @Test
    void findById_returnThrow() {
        //given
        when(foodOrderingRequestDAO.findById(anyInt()))
                .thenReturn(Optional.empty());

        //when
        //then
        assertThrows(NotFoundException.class, () -> foodOrderingRequestService.findById(anyInt()));
    }

    @Test
    void createTally_returnVoidListOrders() {
        //given
        var list = List.of(order1(), order2());
        ArgumentCaptor<FoodOrderingRequest> captor = ArgumentCaptor.forClass(FoodOrderingRequest.class);
        when(foodOrderingRequestDAO.save(any(FoodOrderingRequest.class)))
                .thenReturn(foodOrderingRequest1());

        //when
        foodOrderingRequestService.createTally(customer1(), list);

        //then
        verify(foodOrderingRequestDAO).save(captor.capture());
        verify(orderService, times(list.size())).create(any(Order.class));
        assertEquals(customer1(), captor.getValue().getCustomer());
    }

    @Test
    void delete_returnVoid() {
        //given
        Integer iter = 10;
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);

        //when
        foodOrderingRequestService.delete(iter);

        //then
        verify(foodOrderingRequestDAO).delete(captor.capture());
        assertEquals(iter, captor.getValue());
    }

    @Test
    void findAllWithOwner_returnMap() {
        //given
        FoodOrderingRequest for1 = foodOrderingRequest1().withCompleted(true);
        FoodOrderingRequest for2 = foodOrderingRequest2().withCompleted(true);
        FoodOrderingRequest for3 = foodOrderingRequest1().withCompleted(false);
        FoodOrderingRequest for4 = foodOrderingRequest2().withCompleted(false);
        var map = Map.of(
                true, Set.of(for1, for2),
                false, Set.of(for3, for4)
        );
        Order order1 = Order.builder()
                .orderaCode("1112")
                .quantity(2)
                .foodOrderingRequestEntity(for1)
                .build();
        Order order2 = Order.builder()
                .orderaCode("1212")
                .quantity(3)
                .foodOrderingRequestEntity(for2)
                .build();
        Order order3 = Order.builder()
                .orderaCode("1132")
                .quantity(2)
                .foodOrderingRequestEntity(for3)
                .build();
        Order order4 = Order.builder()
                .orderaCode("5112")
                .quantity(2)
                .foodOrderingRequestEntity(for4)
                .build();
        Product product1 = Product.builder()
                .productCode("345667")
                .type(ProductType.APPETIZER)
                .name("asddas")
                .description("123131313nxvcnm xcvn")
                .price(BigDecimal.TEN)
                .orderEntities(Set.of(order1, order3))
                .build();
        Product product2 = Product.builder()
                .productCode("11324424534")
                .type(ProductType.DISH)
                .name("zcxbvbcv")
                .description("99999 akdaj andana ")
                .price(BigDecimal.ONE)
                .orderEntities(Set.of(order2, order4))
                .build();
        Place place1 = Place.builder()
                .phone("+48 888 444 111")
                .name("PlaceName")
//                .productEntities(Set.of(product1))
                .build();
        Place place2 = Place.builder()
                .phone("+48 999 444 111")
                .name("PlaceName2")
//                .productEntities(Set.of(product2))
                .build();

        var listProduct = List.of(product1, product2);
        var listPlace = List.of(place1, place2);
        var listOrders = List.of(order1, order2, order3, order4);
        when(productService.findAllProductWithPlace(any(Place.class)))
                .thenReturn(listProduct);
        when(orderService.findAllOrderWithProduct(any(Product.class)))
                .thenReturn(listOrders);

        //when
        var result = foodOrderingRequestService.findAllWithOwner(listPlace);

        //then
        assertEquals(result.get(true).size(), 2);
        assertEquals(result.get(false).size(), 2);
        assertEquals(result, map);
    }

    @Test
    void completed_returnVoid() {
        Integer iter = 10;
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);

        //when
        foodOrderingRequestService.completed(iter);

        //then
        verify(foodOrderingRequestDAO).completed(captor.capture());
        assertEquals(iter, captor.getValue());
    }

    @Test
    void findAllActive_returnListFoodOrderingRequest() {
        //given
        var list = List.of(foodOrderingRequest1(), foodOrderingRequest2());
        when(foodOrderingRequestDAO.findAllActive())
                .thenReturn(list);

        //when
        var result = foodOrderingRequestService.findAllActive();

        //then
        assertEquals(result.size(), 2);
    }
}