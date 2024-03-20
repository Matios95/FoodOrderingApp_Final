package pl.matek.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.matek.domain.Customer;
import pl.matek.domain.FoodOrderingRequest;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FoodOrderingRequestService {

    public List<FoodOrderingRequest> findAllWithCustomer(Customer customer) {

        //todo
        return null;
    }
}
