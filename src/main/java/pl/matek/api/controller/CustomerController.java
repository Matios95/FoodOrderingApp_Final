package pl.matek.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.matek.api.dto.FoodOrderingRequestDTO;
import pl.matek.api.dto.SearchStreetDTO;
import pl.matek.api.dto.mapper.CustomerMapper;
import pl.matek.api.dto.mapper.FoodOrderingRequestMapper;
import pl.matek.business.CustomerService;
import pl.matek.business.FoodOrderingRequestService;
import pl.matek.domain.Customer;

import java.util.List;

@Controller
@AllArgsConstructor
public class CustomerController {

    static final String CUSTOMER = "/customer";

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final FoodOrderingRequestService foodOrderingRequestService;
    private final FoodOrderingRequestMapper foodOrderingRequestMapper;

    @GetMapping(value = CUSTOMER)
    public String customerPanel(
            Authentication authentication,
            HttpServletRequest request,
            Model model
    ) {
        String email = authentication.getName();
        SearchStreetDTO searchStreetDTO = new SearchStreetDTO();
        List<FoodOrderingRequestDTO> foodOrderingRequestDTOs = foodOrderingRequestService
                .findAllWithCustomer(getCustomer(email)).stream()
                .map(customerMapper::map)
                .toList();
        model.addAttribute("foodOrderingRequestDTOs", foodOrderingRequestDTOs);
        model.addAttribute("searchStreetDTO", searchStreetDTO);
        return "customer";
    }


    private Customer getCustomer(String email) {
        return customerService.findCustomer(email);
    }
}
