package pl.matek.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.domain.Customer;
import pl.matek.domain.exception.NotFoundException;
import pl.matek.infrastructure.database.repository.CustomerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static pl.matek.util.EntityFixtures.customer1;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerDAO;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void findCustomer_returnThrow() {
        //given
        when(customerDAO.findByCustomer(anyString()))
                .thenReturn(Optional.empty());

        //when //then
        assertThrows(NotFoundException.class,
                () -> customerService.findCustomer(anyString()));
    }

    @Test
    void findCustomer_returnCustomer() {
        //given
        when(customerDAO.findByCustomer(anyString()))
                .thenReturn(Optional.ofNullable(customer1()));

        //when
        Customer customer = customerService.findCustomer(customer1().getEmail());

        //then
        assertEquals(customer1(), customer);
    }

    @Test
    void customerCreate_returnVoid() {
        //given
        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        //when
        customerService.customerCreate(customer1());

        // then
        verify(customerDAO).customerCreate(captor.capture());
        assertEquals(customer1(), captor.getValue());
    }
}