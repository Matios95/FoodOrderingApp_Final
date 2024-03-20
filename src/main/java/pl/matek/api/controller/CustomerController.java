package pl.matek.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.matek.api.dto.FoodOrderingRequestDTO;
import pl.matek.api.dto.OrderDTO;
import pl.matek.api.dto.SearchStreetDTO;
import pl.matek.api.dto.mapper.CustomerMapper;
import pl.matek.api.dto.mapper.FoodOrderingRequestMapper;
import pl.matek.api.dto.mapper.OrderMapper;
import pl.matek.business.CustomerService;
import pl.matek.business.FoodOrderingRequestService;
import pl.matek.business.OrderService;
import pl.matek.domain.Customer;
import pl.matek.domain.FoodOrderingRequest;

import java.util.List;

@Controller
@AllArgsConstructor
public class CustomerController {

    static final String CUSTOMER = "/customer";
    static final String CUSTOMER_TALLY = "/customer/tally/{forID}";
    static final String SEARCH_PLACES = "/customer/searchPlaces";

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final FoodOrderingRequestService foodOrderingRequestService;
    private final FoodOrderingRequestMapper foodOrderingRequestMapper;
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping(value = CUSTOMER)
    public String customerPanel(
            Authentication authentication,
            Model model
    ) {
        //todo dodac przycisk info i cancel
        String email = authentication.getName();
        Customer customer = getCustomer(email);
        SearchStreetDTO searchStreetDTO = new SearchStreetDTO();
        List<FoodOrderingRequestDTO> foodOrderingRequestDTOs = foodOrderingRequestService
                .findAllWithCustomer(customer).stream()
                .map(customerMapper::map)
                .toList();
        model.addAttribute("foodOrderingRequestDTOs", foodOrderingRequestDTOs);
        model.addAttribute("searchStreetDTO", searchStreetDTO);
        return "customer";
    }

    @GetMapping(value = CUSTOMER_TALLY)
    public String tally(
            @PathVariable("forID") Integer forId,
            Model model
    ) {
        FoodOrderingRequest foodOrderingRequest = foodOrderingRequestService.findById(forId);
        FoodOrderingRequestDTO foodOrderingRequestDTO = foodOrderingRequestMapper.map(foodOrderingRequest);
        List<OrderDTO> orderDTOs = orderService.findByFoodOrderingRequest(foodOrderingRequest).stream()
                .map(orderMapper::map)
                .toList();
        model.addAttribute("orderDTOs", orderDTOs);
        model.addAttribute("foodOrderingRequestDTO", foodOrderingRequestDTO);
        return "tally";
    }

    @PostMapping(value = SEARCH_PLACES)
    public String searchPlaces(
            @RequestParam(name = "postCode") String postCode,
            @RequestParam(name = "street") String street,
            Authentication authentication,
            Model model
    ) {
        //<form th:action="@{/resetPassword(email=${email})}" method="post">
//        List<>

        return "searchPanel";
    }

    private Customer getCustomer(String email) {
        return customerService.findCustomer(email);
    }
}
