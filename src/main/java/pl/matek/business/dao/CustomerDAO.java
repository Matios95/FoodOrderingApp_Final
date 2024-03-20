package pl.matek.business.dao;

import pl.matek.domain.Customer;

import java.util.Optional;

public interface CustomerDAO {
    Optional<Customer> findByCustomer(String email);
}
