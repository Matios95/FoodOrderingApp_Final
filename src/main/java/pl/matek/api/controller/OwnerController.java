package pl.matek.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.matek.api.dto.DeliveryAddressDTO;
import pl.matek.api.dto.FoodOrderingRequestDTO;
import pl.matek.api.dto.PlaceDTO;
import pl.matek.api.dto.ProductDTO;
import pl.matek.api.dto.mapper.DeliveryAddressMapper;
import pl.matek.api.dto.mapper.FoodOrderingRequestMapper;
import pl.matek.api.dto.mapper.PlaceMapper;
import pl.matek.api.dto.mapper.ProductMapper;
import pl.matek.business.*;
import pl.matek.domain.FoodOrderingRequest;
import pl.matek.domain.Owner;
import pl.matek.domain.Place;
import pl.matek.domain.exception.ProcessingException;
import pl.matek.infrastructure.database.entity.ProductType;
import pl.matek.infrastructure.database.repository.jpa.FoodOrderingRequesJpaRepository;

import java.util.*;

@Controller
@AllArgsConstructor
public class OwnerController {

    static final String OWNER = "/owner";
    static final String PLACE_CREATE = "/owner/placeCreate";
    static final String PLACE_CREATE_ADD = "/owner/placeCreate/menu";
    static final String PLACE_CREATE_MENU = "/owner/placeCreate/{placeId}/menu";
    static final String PRODUCT_ADD = "/owner/placeCreate/{placeId}/productAdd";
    static final String DELIVERY_ADDRESS = "/owner/placeCreate/{placeId}/deliveryAddress";
    static final String DELIVERY_ADDRESS_ADD = "/owner/placeCreate/{placeId}/deliveryAddressAdd";
    static final String COMPLETED_ORDER = "/owner/completedOrder/{forId}";

    private final OwnerService ownerService;
    private final PlaceMapper placeMapper;
    private final PlaceService placeService;
    private final ProductMapper productMapper;
    private final ProductService productService;
    private final DeliveryAddressMapper deliveryAddressMapper;
    private final DeliveryAddressService deliveryAddressService;
    private final FoodOrderingRequestMapper foodOrderingRequestMapper;
    private final FoodOrderingRequestService foodOrderingRequestService;


    private final FoodOrderingRequesJpaRepository foodOrderingRequesJpaRepository;


    @GetMapping(value = OWNER)
    public String ownerPanel(
            Authentication authentication,
            HttpServletRequest request,
            Model model
    ) {
        String email = authentication.getName();
        List<Place> allPlaceWithOwner = placeService.findAllPlaceWithOwner(email);
        List<PlaceDTO> allPlaceOwner = allPlaceWithOwner.stream()
                .map(placeMapper::map)
                .toList();

        Map<Boolean, Set<FoodOrderingRequest>> allWithOwner = foodOrderingRequestService.findAllWithOwner(allPlaceWithOwner);

        List<FoodOrderingRequestDTO> foodOrderingRequestDTOsFalse = allWithOwner.getOrDefault(false, Collections.emptySet()).stream()
                .map(foodOrderingRequestMapper::map)
                .toList();
        List<FoodOrderingRequestDTO> foodOrderingRequestDTOsTrue = allWithOwner.getOrDefault(true, Collections.emptySet()).stream()
                .map(foodOrderingRequestMapper::map)
                .toList();

        model.addAttribute("OwnerName", "Hello: " + getOwner(email).getName());
        model.addAttribute("PlaceDTOs", allPlaceOwner);
        model.addAttribute("FORDTOsFalse", foodOrderingRequestDTOsFalse);
        model.addAttribute("FORDTOsTrue", foodOrderingRequestDTOsTrue);
        return "owner";
    }

    @GetMapping(value = PLACE_CREATE)
    public String placeCreatePanel(Model model) {
        model.addAttribute("PlaceDTO", new PlaceDTO());
        return "placeCreate";
    }

    @PostMapping(value = PLACE_CREATE_ADD)
    private String placeCreateAdd(
            @Valid @ModelAttribute("PlaceDTO") PlaceDTO placeDTO,
            Authentication authentication,
            HttpServletRequest request
    ) {
        String email = authentication.getName();
        placeService.placeCreate(placeMapper.map(placeDTO).withOwner(getOwner(email)));
        return "redirect:" + OWNER;
    }

    @GetMapping(value = PLACE_CREATE_MENU)
    public String menuPanel(
            @PathVariable("placeId") Integer placeId,
            Authentication authentication,
            HttpServletRequest request,
            Model model
    ) {
        checkOwner(placeId, authentication);
        ProductDTO productDTO = ProductDTO.builder()
                .type(EnumSet.allOf(ProductType.class).stream().map(Enum::name).toList())
                .build();
        List<ProductDTO> productDTOs = productService.findAllProductWithPlace(getPlace(placeId)).stream()
                .map(productMapper::map)
                .toList();
        model.addAttribute("ProductDTOs", productDTOs);
        model.addAttribute("ProductDTO", productDTO);
        model.addAttribute("PlaceID", placeId);
        return "menuCreate";
    }

    @PostMapping(value = PRODUCT_ADD)
    public String productAdd(
            @ModelAttribute("ProductDTO") ProductDTO productDTO,
            @PathVariable("placeId") Integer placeId,
            Authentication authentication,
            HttpServletRequest request
    ) {
        checkOwner(placeId, authentication);
        final String productCode = UUID.randomUUID().toString();
        productService.productCreate(productMapper.map(productDTO)
                .withPlace(getPlace(placeId))
                .withProductCode(productCode));
        return "redirect:" + PLACE_CREATE_MENU;
    }

    @GetMapping(value = DELIVERY_ADDRESS)
    public String deliveryAddress(
            @PathVariable("placeId") Integer placeId,
            Authentication authentication,
            Model model
    ) {
        checkOwner(placeId, authentication);
        DeliveryAddressDTO deliveryAddressDTO = new DeliveryAddressDTO();
        List<DeliveryAddressDTO> deliveryAddressDTOs = deliveryAddressService
                .findAllDeliveryWithPlace(getPlace(placeId)).stream()
                .map(deliveryAddressMapper::map)
                .toList();
        model.addAttribute("PlaceID", placeId);
        model.addAttribute("DeliveryAddressDTO", deliveryAddressDTO);
        model.addAttribute("DeliveryAddressDTOs", deliveryAddressDTOs);
        return "deliveryAddress";
    }

    @PostMapping(value = DELIVERY_ADDRESS_ADD)
    public String deliveryAddressAdd(
            @ModelAttribute("DeliveryAddressDTO") DeliveryAddressDTO deliveryAddressDTO,
            @PathVariable("placeId") Integer placeId,
            Authentication authentication

    ) {
        checkOwner(placeId, authentication);
        deliveryAddressService.deliveryAddressCreate(deliveryAddressMapper.map(deliveryAddressDTO)
                .withPlaceDeliveryAddress(getPlace(placeId)));
        return "redirect:" + DELIVERY_ADDRESS;
    }

    @GetMapping(value = COMPLETED_ORDER)
    public String completedOrder(
            @PathVariable("forId") Integer forId,
            Authentication authentication
    ){
        foodOrderingRequestService.completed(forId);
        return "redirect:" + OWNER;
    }

    private Owner getOwner(String email) {
        return ownerService.findOwner(email);
    }

    private Place getPlace(Integer placeId) {
        Place place = placeService.findById(placeId);
        return place;
    }

    private void checkOwner(Integer placeId, Authentication authentication) {
        String email = authentication.getName();
        Owner owner = getOwner(email);
        if (!owner.equals(getPlace(placeId).getOwner())) {
            throw new ProcessingException("Could not view placeId [%s] for owner [%s]".formatted(placeId, email));
        }
    }
}
