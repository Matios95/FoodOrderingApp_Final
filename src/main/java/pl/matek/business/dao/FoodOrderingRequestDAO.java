package pl.matek.business.dao;

import pl.matek.domain.Customer;
import pl.matek.domain.FoodOrderingRequest;
import pl.matek.domain.Place;

import java.util.List;
import java.util.Optional;

public interface FoodOrderingRequestDAO {
    List<FoodOrderingRequest> findAllWithCustomer(Customer customer);

    Optional<FoodOrderingRequest> findById(Integer forId);

    FoodOrderingRequest save(FoodOrderingRequest foodOrderingRequest);

    void delete(Integer forId);

    void completed(Integer forId);

    List<FoodOrderingRequest> findAllActive();
}
