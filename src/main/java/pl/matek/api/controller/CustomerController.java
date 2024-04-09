package pl.matek.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.matek.api.dto.*;
import pl.matek.api.dto.mapper.FoodOrderingRequestMapper;
import pl.matek.api.dto.mapper.OrderMapper;
import pl.matek.api.dto.mapper.PlaceMapper;
import pl.matek.api.dto.mapper.ProductMapper;
import pl.matek.business.*;
import pl.matek.domain.Customer;
import pl.matek.domain.FoodOrderingRequest;
import pl.matek.domain.Order;
import pl.matek.domain.Place;
import pl.matek.domain.exception.NotFoundException;
import pl.matek.domain.exception.ProcessingException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class CustomerController {

    static final String CUSTOMER = "/customer";
    static final String CREATE_ORDER = "/customer/createOrder";
    static final String PLACE_MENU = "/customer/{placeID}/menu";
    static final String SEARCH_PLACES = "/customer/searchPlaces";
    static final String TALLY_INFO = "/customer/tally/{forId}/info";
    static final String TALLY_DELETE = "/customer/tally/{forId}/delete";

    static final Integer PAGE_SIZE = 6;
    static final Long MAX_MINUTE_DELETE_ORDER = 20L;

    private final CustomerService customerService;
    private final FoodOrderingRequestService foodOrderingRequestService;
    private final FoodOrderingRequestMapper foodOrderingRequestMapper;
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final PlaceService placeService;
    private final PlaceMapper placeMapper;
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping(value = CUSTOMER)
    public String customerPanel(Authentication authentication, Model model) {
        Customer customer = getCustomer(authentication);
        List<FoodOrderingRequestDTO> foodOrderingRequestDTOs = foodOrderingRequestService.findAllWithCustomer(customer).stream()
                .filter(foodOrderingRequest -> !foodOrderingRequest.getCompleted())
                .map(foodOrderingRequestMapper::map)
                .toList();
        model.addAttribute("searchPlace", new SearchPlacesDTO());
        model.addAttribute("foodOrderingRequestDTOs", foodOrderingRequestDTOs);
        model.addAttribute("CustomerName", "Hello: " + customer.getName());
        return "customer";
    }

    @GetMapping(value = SEARCH_PLACES)
    public String searchPlaces(
            @Valid @ModelAttribute("searchPlace") SearchPlacesDTO searchPlacesDTO,
            @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
            @RequestParam(value = "sortField", required = false, defaultValue = "phone") String sortField,
            @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir, Model model) {

        Page<Place> allPlaceWithParam = placeService.findAllPlaceWithParam(
                searchPlacesDTO.getPostcode(), searchPlacesDTO.getStreet(), pageNo, PAGE_SIZE, sortDir, sortField);

        List<PlaceDTO> placeDTOs = allPlaceWithParam.stream().map(placeMapper::map).toList();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("sortField", sortField);
        model.addAttribute("totalPages", allPlaceWithParam.getTotalPages());
        model.addAttribute("totalItems", allPlaceWithParam.getTotalElements());

        model.addAttribute("sortDir", sortDir);
        model.addAttribute("PlaceDTOs", placeDTOs);
        model.addAttribute("searchPlace", searchPlacesDTO);
        model.addAttribute("reversSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return "searchPanel";
    }

    @GetMapping(value = PLACE_MENU)
    public String placeMenu(@PathVariable("placeID") Integer placeId, Model model) {
        List<MenuDTO> menuDTOs = productService.findAllProductWithPlace(getPlace(placeId)).stream()
                .map(productMapper::mapMenu).toList();
        model.addAttribute("MenuDTOs", new MenusDTO(menuDTOs));
        return "placeMenu";
    }

    @PostMapping(CREATE_ORDER)
    public String createOrder(@Valid @ModelAttribute("MenuDTOs") MenusDTO menusDTO, Authentication authentication, Model model) {
        if (Objects.isNull(menusDTO.getMenuDTOList())) {
            throw new NotFoundException("Not found product by created ordering");
        }
        Customer customer = getCustomer(authentication);
        List<Order> orders = menusDTO.getMenuDTOList().stream()
                .filter(x -> !Objects.isNull(x.getQuantity()) && x.getQuantity() > 0)
                .map(menuDTO -> orderMapper.map(menuDTO)
                        .withProduct(productService.findByCode(menuDTO.getProductCode())))
                .toList();
        foodOrderingRequestService.createTally(customer, orders);
        return "redirect:/";
    }

    @GetMapping(value = TALLY_DELETE)
    public String tallyDelete(@PathVariable("forId") Integer forId, Authentication authentication, Model model) {
        checkCustomer(forId, authentication);
        FoodOrderingRequest foodOrderingRequest = foodOrderingRequestService.findById(forId);
        OffsetDateTime orderTime = foodOrderingRequest.getDatetime();
        OffsetDateTime maxTimeDeleteOrder = orderTime.plusMinutes(MAX_MINUTE_DELETE_ORDER);
        if (maxTimeDeleteOrder.isBefore(OffsetDateTime.now())) {
            throw new ProcessingException("It is too late to delete order [%s]".formatted(forId));
        }
        foodOrderingRequestService.delete(forId);
        return "redirect:/";
    }

    @GetMapping(value = TALLY_INFO)
    public String tallyInfo(
            @PathVariable("forId") Integer forId,
            Model model) {
        FoodOrderingRequest foodOrderingRequest = foodOrderingRequestService.findById(forId);
        FoodOrderingRequestDTO foodOrderingRequestDTO = foodOrderingRequestMapper.map(foodOrderingRequest);
        List<OrderDTO> orderDTOs = orderService.findByFoodOrderingRequest(foodOrderingRequest).stream().map(orderMapper::map).toList();
        model.addAttribute("foodOrderingRequestDTO", foodOrderingRequestDTO);
        model.addAttribute("orderDTOs", orderDTOs);
        return "tally";
    }

    private void checkCustomer(Integer forId, Authentication authentication) {
        Customer customer = getCustomer(authentication);
        if (!customer.equals(getFor(forId).getCustomer())) {
            throw new ProcessingException("Could not view food ordering request [%s] for customer [%s]".formatted(forId, customer.getEmail()));
        }
    }

    private FoodOrderingRequest getFor(Integer forId) {
        return foodOrderingRequestService.findById(forId);
    }

    private Place getPlace(Integer placeId) {
        return placeService.findById(placeId);
    }


    private Customer getCustomer(Authentication authentication) {
        String email = authentication.getName();
        return customerService.findCustomer(email);
    }
}
