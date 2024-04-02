package pl.matek.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.matek.business.dao.CustomerDAO;
import pl.matek.domain.Customer;
import pl.matek.infrastructure.database.entity.CustomerEntity;
import pl.matek.infrastructure.database.repository.jpa.CustomerJpaRepository;
import pl.matek.infrastructure.database.repository.mapper.CustomerEntityMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class CustomerRepository implements CustomerDAO {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerEntityMapper customerEntityMapper;

    @Override
    public Optional<Customer> findByCustomer(String email) {
        Optional<CustomerEntity> saved = customerJpaRepository.findByEmail(email);
        return saved.map(customerEntityMapper::mapFromEntity);
    }

    @Override
    public Customer customerCreate(Customer customer) {
        CustomerEntity toSave = customerEntityMapper.mapToEntity(customer);
        CustomerEntity saved = customerJpaRepository.save(toSave);
        return customerEntityMapper.mapFromEntity(saved);
    }
}
