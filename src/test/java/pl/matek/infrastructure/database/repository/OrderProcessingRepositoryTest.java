package pl.matek.infrastructure.database.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.domain.FoodOrderingRequest;
import pl.matek.domain.Order;
import pl.matek.domain.Product;
import pl.matek.infrastructure.database.entity.FoodOrderingRequestEntity;
import pl.matek.infrastructure.database.entity.OrderEntity;
import pl.matek.infrastructure.database.entity.ProductEntity;
import pl.matek.infrastructure.database.repository.jpa.OrderJpaRepository;
import pl.matek.infrastructure.database.repository.mapper.FoodOrderingRequestEntityMapperImpl;
import pl.matek.infrastructure.database.repository.mapper.OrderEntityMapperImpl;
import pl.matek.infrastructure.database.repository.mapper.ProductEntityMapperImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static pl.matek.util.EntityFixtures.*;

@ExtendWith(MockitoExtension.class)
class OrderProcessingRepositoryTest {

    @Mock
    private OrderEntityMapperImpl orderEntityMapper;

    @Mock
    private OrderJpaRepository orderJpaRepository;

    @Mock
    private FoodOrderingRequestEntityMapperImpl foodOrderingRequestEntityMapper;

    @Mock
    private ProductEntityMapperImpl productEntityMapper;

    @InjectMocks
    private OrderProcessingRepository orderProcessingRepository;

    @Test
    void findByFoodOrderingRequest() {
        //given
        var listEntity = List.of(orderEntity1(), orderEntity2());
        var list = List.of(order1(), order2());
        when(foodOrderingRequestEntityMapper.mapToEntity(any(FoodOrderingRequest.class)))
                .thenReturn(foodOrderingRequestEntity1());
        when(orderJpaRepository.findAllByFoodOrderingRequestEntity(any(FoodOrderingRequestEntity.class)))
                .thenReturn(listEntity);
        when(orderEntityMapper.mapFromEntity(any(OrderEntity.class)))
                .thenCallRealMethod();

        //when
        var result = orderProcessingRepository.findByFoodOrderingRequest(foodOrderingRequest1());

        //then
        assertEquals(listEntity.size(), 2);
        assertEquals(list, result);
    }

    @Test
    void create() {
        //given
        var method = mock(OrderProcessingRepository.class);
        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        doNothing().when(method).create(captor.capture());

        //when
        method.create(order1());

        //then
        assertEquals(order1(), captor.getValue());
    }

    @Test
    void findAllOrderWithProduct() {
        //given
        var listOrder = List.of(order1(), order2());
        var listEntity = List.of(orderEntity1(), orderEntity2());
        when(productEntityMapper.mapToEntity(any(Product.class)))
                .thenReturn(productEntity1());
        when(orderJpaRepository.findAllByProduct(any(ProductEntity.class)))
                .thenReturn(listEntity);
        when(orderEntityMapper.mapFromEntity(any(OrderEntity.class)))
                .thenCallRealMethod();

        //when
        var result = orderProcessingRepository.findAllOrderWithProduct(product1());

        //then
        assertEquals(result.size(), 2);
        assertEquals(listOrder, result);
    }
}