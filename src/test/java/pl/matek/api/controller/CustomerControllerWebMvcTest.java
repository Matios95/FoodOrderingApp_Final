//package pl.matek.api.controller;
//
//import lombok.AllArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import pl.matek.api.dto.mapper.*;
//import pl.matek.business.FoodOrderingRequestService;
//import pl.matek.business.OrderService;
//import pl.matek.business.ProductService;
//import pl.matek.domain.FoodOrderingRequest;
//import pl.matek.domain.Order;
//import pl.matek.domain.Place;
//import pl.matek.domain.Product;
//import pl.matek.infrastructure.database.entity.ProductType;
//
//import java.math.BigDecimal;
//import java.time.OffsetDateTime;
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static pl.matek.util.EntityFixtures.place2;
//
//@WebMvcTest(controllers = CustomerController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@AllArgsConstructor(onConstructor = @__(@Autowired))
//public class CustomerControllerWebMvcTest {
//
//    private MockMvc mockMvc;
//
//    @MockBean
//    private FoodOrderingRequestService foodOrderingRequestService;
//    @MockBean
//    private FoodOrderingRequestMapperImpl foodOrderingRequestMapper;
//    @MockBean
//    private OrderService orderService;
//    @MockBean
//    private OrderMapperImpl orderMapper;
//    @MockBean
//    private ProductService productService;
//    @MockBean
//    private ProductMapperImpl productMapper;
//
//    @Test
//    void viewPanelCorrectly() throws Exception {
//        //given
//        Product menuDTO1 = Product.builder()
//                .productId(1)
//                .productCode(UUID.randomUUID().toString())
//                .name("MenuName1")
//                .type(ProductType.APPETIZER)
//                .description("description menu 1")
//                .price(BigDecimal.TEN)
//                .place(place2())
//                .build();
//        Product menuDTO2 = Product.builder()
//                .productId(2)
//                .productCode(UUID.randomUUID().toString())
//                .name("MenuName2")
//                .type(ProductType.DESSERT)
//                .description("description menu 2")
//                .price(BigDecimal.ONE)
//                .place(place2())
//                .build();
//
//        var list = List.of(menuDTO1, menuDTO2);
//        when(productService.findAllProductWithPlace(any(Place.class)))
//                .thenReturn(list);
//        when(productMapper.map(any(Product.class)))
//                .thenCallRealMethod();
//        //when
//        //then
//        mockMvc.perform(get("/customer/" + place2().getPlaceId() + "/menu")
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("MenuDTOs"))
//                .andExpect(view().name("placeMenu"));
//    }
//
//    @Test
//    void viewTallyInfoCorrectly() throws Exception {
//        //given
//        FoodOrderingRequest foodOrderingRequest = FoodOrderingRequest.builder()
//                .foodOrderingRequestId(20)
//                .foodOrderingRequestCode(String.valueOf(UUID.randomUUID()))
//                .datetime(OffsetDateTime.now())
//                .completed(false)
//                .build();
//        Order order1 = Order.builder()
//                .orderaId(10)
//                .orderaCode(UUID.randomUUID().toString())
//                .quantity(3)
//                .foodOrderingRequestEntity(foodOrderingRequest)
//                .product(Product.builder().build())
//                .build();
//        Order order2 = Order.builder()
//                .orderaId(10)
//                .orderaCode(UUID.randomUUID().toString())
//                .quantity(2)
//                .foodOrderingRequestEntity(foodOrderingRequest)
//                .product(Product.builder().build())
//                .build();
//        var list = List.of(order1, order2);
//        when(foodOrderingRequestService.findById(anyInt()))
//                .thenReturn(foodOrderingRequest);
//        when(foodOrderingRequestMapper.map(any(FoodOrderingRequest.class)))
//                .thenCallRealMethod();
//        when(orderService.findByFoodOrderingRequest(any(FoodOrderingRequest.class)))
//                .thenReturn(list);
//        when(orderMapper.map(any(Order.class)))
//                .thenCallRealMethod();
//
//        //when
//        //then
//        mockMvc.perform(get("/customer/tally/" + foodOrderingRequest.getFoodOrderingRequestId() + "/info")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("foodOrderingRequestDTO"))
//                .andExpect(model().attributeExists("orderDTOs"))
//                .andExpect(view().name("tally"));
//    }
//}