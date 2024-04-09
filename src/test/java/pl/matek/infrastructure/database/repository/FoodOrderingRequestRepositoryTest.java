package pl.matek.infrastructure.database.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.domain.Customer;
import pl.matek.domain.FoodOrderingRequest;
import pl.matek.domain.exception.NotFoundException;
import pl.matek.infrastructure.database.entity.CustomerEntity;
import pl.matek.infrastructure.database.entity.FoodOrderingRequestEntity;
import pl.matek.infrastructure.database.repository.jpa.FoodOrderingRequesJpaRepository;
import pl.matek.infrastructure.database.repository.mapper.CustomerEntityMapperImpl;
import pl.matek.infrastructure.database.repository.mapper.FoodOrderingRequestEntityMapperImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pl.matek.util.EntityFixtures.*;

@ExtendWith(MockitoExtension.class)
class FoodOrderingRequestRepositoryTest {

    @Mock
    private FoodOrderingRequestEntityMapperImpl foodOrderingRequestEntityMapper;

    @Mock
    private FoodOrderingRequesJpaRepository foodOrderingRequesJpaRepository;

    @Mock
    private CustomerEntityMapperImpl customerEntityMapper;

    @InjectMocks
    private FoodOrderingRequestRepository foodOrderingRequestRepository;

    @Test
    void findAllWithCustomer() {
        //given
        var list = List.of(foodOrderingRequest1(), foodOrderingRequest2());
        var listEntity = List.of(foodOrderingRequestEntity1(), foodOrderingRequestEntity2());
        when(customerEntityMapper.mapToEntity(any(Customer.class)))
                .thenCallRealMethod();
        when(foodOrderingRequesJpaRepository.findByCustomer(any(CustomerEntity.class)))
                .thenReturn(listEntity);
        when(foodOrderingRequestEntityMapper.mapFromEntity(any(FoodOrderingRequestEntity.class)))
                .thenCallRealMethod();

        //when
        var result = foodOrderingRequestRepository.findAllWithCustomer(customer1());

        //then
        assertEquals(result.size(), 2);
        assertEquals(result, list);
    }

    @Test
    void findById() {
        //given
        when(foodOrderingRequesJpaRepository.findById(any(Integer.class)))
                .thenReturn(Optional.of(foodOrderingRequestEntity1()));
        when(foodOrderingRequestEntityMapper.mapFromEntity(any(FoodOrderingRequestEntity.class)))
                .thenCallRealMethod();

        //when
        var result = foodOrderingRequestRepository.findById(1);

        //then
        assertFalse(result.isEmpty());
        assertEquals(result.get(), foodOrderingRequest1());
        assertNotEquals(result.get(), foodOrderingRequest2());
    }

    @Test
    void save() {
        //given
        when(foodOrderingRequestEntityMapper.mapToEntity(any(FoodOrderingRequest.class)))
                .thenReturn(foodOrderingRequestEntity1());
        when(foodOrderingRequesJpaRepository.save(any(FoodOrderingRequestEntity.class)))
                .thenReturn(foodOrderingRequestEntity1());
        when(foodOrderingRequestEntityMapper.mapFromEntity(any(FoodOrderingRequestEntity.class)))
                .thenCallRealMethod();

        //when
        var result = foodOrderingRequestRepository.save(foodOrderingRequest1());
        var result2 = foodOrderingRequestRepository.save(foodOrderingRequest2());

        //then
        assertNotNull(result);
        assertNotNull(result2);
        assertEquals(result, foodOrderingRequest1());
    }

    @Test
    void delete() {
        //given
        var method = mock(FoodOrderingRequestRepository.class);
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(method).delete(captor.capture());

        //when
        method.delete(1);

        //then
        assertEquals(1, captor.getValue());
    }

    @Test
    void completedId() {
        //given
        var method = mock(FoodOrderingRequestRepository.class);
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        doNothing().when(method).completed(captor.capture());

        //when
        method.completed(1);

        //then
        assertEquals(1, captor.getValue());


    }

    @Test
    void completedThrow() {
        //given
        var method = mock(FoodOrderingRequestRepository.class);
        doThrow(NotFoundException.class).when(method).completed(anyInt());

        //when //then
        assertThrows(NotFoundException.class, () -> method.completed(2));
    }
}