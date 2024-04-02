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
    private OwnerService ownerService;
    private PlaceService placeService;

    @Transactional
    public List<FoodOrderingRequest> findAllWithCustomer(Customer customer) {
        return foodOrderingRequestDAO.findAllWithCustomer(customer);
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
    }

    @Transactional
    public void delete(Integer forId) {
        foodOrderingRequestDAO.delete(forId);
    }

    @Transactional
    public Set<FoodOrderingRequest> findAllWithOwner(String email) {
//        List<Place> place1 = placeService.findAllPlaceWithOwner(email);

//        TreeSet<FoodOrderingRequest> collect = place1.stream()
//                .map(place -> place.getProductEntities().stream()
//                        .map(product -> product.getOrderEntities().stream()
//                                .map(Order::getFoodOrderingRequestEntity)
//                                .toList())
//                        .toList())
//                .toList().stream()
//                .flatMap(Collection::stream)
//                .flatMap(Collection::stream)
//                .sorted(Comparator.comparing(FoodOrderingRequest::getDatetime))
//                .collect(Collectors.toCollection(TreeSet::new));
//
//        collect.forEach(System.out::println);


//        place1.stream()
//                .map(x -> x.getProductEntities().stream()
//                        .toList()).toList()
//                .stream().flatMap(List::stream).toList();
        return Collections.emptySet();
    }
}
