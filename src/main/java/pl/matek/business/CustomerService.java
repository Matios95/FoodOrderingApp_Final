package pl.matek.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.matek.business.dao.CustomerDAO;
import pl.matek.domain.Customer;
import pl.matek.domain.exception.NotFoundException;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;

    @Transactional
    public Customer findCustomer(String email) {
        Optional<Customer> customer = customerDAO.findByCustomer(email);
        if (customer.isEmpty()) {
            throw new NotFoundException("Not found customer: [%s]".formatted(email));
        }
        return customer.get();
    }

    public Customer customerCreate(Customer customer) {
        return customerDAO.customerCreate(customer);
    }
}
