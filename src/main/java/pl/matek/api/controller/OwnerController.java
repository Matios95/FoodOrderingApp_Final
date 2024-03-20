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
import pl.matek.api.dto.PlaceDTO;
import pl.matek.api.dto.ProductDTO;
import pl.matek.api.dto.mapper.DeliveryAddressMapper;
import pl.matek.api.dto.mapper.PlaceMapper;
import pl.matek.api.dto.mapper.ProductMapper;
import pl.matek.business.DeliveryAddressService;
import pl.matek.business.OwnerService;
import pl.matek.business.PlaceService;
import pl.matek.business.ProductService;
import pl.matek.domain.Owner;
import pl.matek.domain.Place;
import pl.matek.domain.exception.ProcessingException;
import pl.matek.infrastructure.database.entity.ProductType;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

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

    private final OwnerService ownerService;
    private final PlaceService placeService;
    private final ProductService productService;
    private final DeliveryAddressService deliveryAddressService;
    private final PlaceMapper placeMapper;
    private final ProductMapper productMapper;
    private final DeliveryAddressMapper deliveryAddressMapper;

    @GetMapping(value = OWNER)
    public String ownerPanel(
            Authentication authentication,
            HttpServletRequest request,
            Model model
    ) {
        String email = authentication.getName();
        List<PlaceDTO> allPlaceOwner = placeService.findAllPlaceWithOwner(email).stream()
                .map(placeMapper::map)
                .toList();
        model.addAttribute("OwnerName", "Hello: " + getOwner(email).getName());
        model.addAttribute("PlaceDTOs", allPlaceOwner);
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
            HttpServletRequest request,
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
            Authentication authentication,
            HttpServletRequest request

    ) {
        checkOwner(placeId, authentication);
        deliveryAddressService.deliveryAddressCreate(deliveryAddressMapper.map(deliveryAddressDTO)
                .withPlaceDeliveryAddress(getPlace(placeId)));
        return "redirect:" + DELIVERY_ADDRESS;
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
