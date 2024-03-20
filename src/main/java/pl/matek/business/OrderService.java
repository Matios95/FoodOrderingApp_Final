package pl.matek.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.matek.business.dao.FoodOrderingRequestDAO;
import pl.matek.domain.FoodOrderingRequest;
import pl.matek.domain.Order;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {

    private final FoodOrderingRequestDAO foodOrderingRequestDAO;

    @Transactional
    public List<Order> findByFoodOrderingRequest(FoodOrderingRequest foodOrderingRequest) {
        //todo

        return null;
    }
}
