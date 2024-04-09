package pl.matek.infrastructure.database.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.domain.Customer;
import pl.matek.infrastructure.database.entity.CustomerEntity;
import pl.matek.infrastructure.database.repository.jpa.CustomerJpaRepository;
import pl.matek.infrastructure.database.repository.mapper.CustomerEntityMapper;
import pl.matek.infrastructure.database.repository.mapper.CustomerEntityMapperImpl;
import pl.matek.util.EntityFixtures;

import java.util.Optional;

import static pl.matek.util.EntityFixtures.*;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryTest {

    @Mock
    private CustomerJpaRepository customerJpaRepository;

    @Mock
    private CustomerEntityMapperImpl customerEntityMapper;

    @InjectMocks
    private CustomerRepository customerRepository;

    @Test
    void findByCustomer() {
        //give
        Mockito.when(customerJpaRepository.findByEmail(Mockito.any(String.class)))
                .thenReturn(Optional.of(customerEntity1()));
        Mockito.when(customerEntityMapper.mapFromEntity(Mockito.any(CustomerEntity.class)))
                .thenCallRealMethod();

        //when
        Optional<Customer> findCustomer = customerRepository.findByCustomer("email@gmail.com");

        //then
        Assertions.assertEquals(customer1(), findCustomer.get());
    }

    @Test
    void customerCreate() {
        //given
        Mockito.when(customerEntityMapper.mapFromEntity(Mockito.any(CustomerEntity.class)))
                .thenCallRealMethod();
        Mockito.when(customerJpaRepository.save(Mockito.any(CustomerEntity.class)))
                .thenReturn(customerEntity1());
        Mockito.when(customerEntityMapper.mapToEntity(Mockito.any(Customer.class)))
                .thenCallRealMethod();

        //when
        Customer customerCreated = customerRepository.customerCreate(customer1());

        //then
        Assertions.assertNotNull(customerCreated);
        Assertions.assertEquals(customer1(), customerCreated);
    }
}