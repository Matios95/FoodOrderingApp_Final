package pl.matek.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.matek.business.dao.FoodOrderingRequestDAO;
import pl.matek.domain.Customer;
import pl.matek.domain.FoodOrderingRequest;
import pl.matek.domain.Order;
import pl.matek.domain.Place;
import pl.matek.domain.exception.NotFoundException;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FoodOrderingRequestService {

    private FoodOrderingRequestDAO foodOrderingRequestDAO;
    private OrderService orderService;
    private ProductService productService;

    @Transactional
    public List<FoodOrderingRequest> findAllWithCustomer(Customer customer) {
        List<FoodOrderingRequest> allWithCustomer = foodOrderingRequestDAO.findAllWithCustomer(customer);
        log.info("Available food ordering request with customer: [%s]".formatted(allWithCustomer.size()));
        return allWithCustomer;
    }

    @Transactional
    public FoodOrderingRequest findById(Integer forId) {
        Optional<FoodOrderingRequest> foodOrderingRequest = foodOrderingRequestDAO.findById(forId);
        if (foodOrderingRequest.isEmpty()) {
            throw new NotFoundException("Not found food ordering request: [%s]".formatted(forId));
        }
        return foodOrderingRequest.get();
    }

    @Transactional
    public void createTally(Customer customer, List<Order> orders) {
        FoodOrderingRequest request = foodOrderingRequestDAO.save(FoodOrderingRequest.builder()
                .foodOrderingRequestCode(UUID.randomUUID().toString())
                .datetime(OffsetDateTime.now())
                .customer(customer)
                .completed(false)
                .build());
        orders.forEach(x -> orderService.create(x
                .withFoodOrderingRequestEntity(request)
                .withOrderaCode(UUID.randomUUID().toString())));
        log.debug("Tally create: [%s]".formatted(customer.getCustomerId()));
    }

    @Transactional
    public void delete(Integer forId) {
        log.debug("Food ordering is delete: [%s]".formatted(forId));
        foodOrderingRequestDAO.delete(forId);
    }

    @Transactional
    public Map<Boolean, Set<FoodOrderingRequest>> findAllWithOwner(List<Place> allPlaceOwner) {

        Map<Boolean, Set<FoodOrderingRequest>> collect = allPlaceOwner.stream()
                .map(place -> productService.findAllProductWithPlace(place).stream()
                        .map(product -> orderService.findAllOrderWithProduct(product).stream()
                                .map(Order::getFoodOrderingRequestEntity)
                                .toList())
                        .flatMap(List::stream)
                        .toList())
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(
                        FoodOrderingRequest::getCompleted, Collectors.toSet()));
        log.info("Available food ordering request: [%s]".formatted(collect.values().size()));
        return collect;
    }

    @Transactional
    public void completed(Integer forId) {
        log.debug("Food ordering is completed: [%s]".formatted(forId));
        foodOrderingRequestDAO.completed(forId);
    }

    @Transactional
    public List<FoodOrderingRequest> findAllActive() {
        List<FoodOrderingRequest> allActive = foodOrderingRequestDAO.findAllActive();
        log.info("Available food ordering request is active: [%s]".formatted(allActive.size()));
        return allActive;
    }

}
