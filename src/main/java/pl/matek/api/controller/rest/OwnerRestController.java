package pl.matek.api.controller.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.matek.api.dto.OwnerDTO;
import pl.matek.api.dto.PlaceDTO;
import pl.matek.api.dto.mapper.OwnerMapper;
import pl.matek.api.dto.mapper.PlaceMapper;
import pl.matek.business.FoodOrderingRequestService;
import pl.matek.business.OwnerService;
import pl.matek.business.PlaceService;

import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping(OwnerRestController.API)
public class OwnerRestController {

    static final String API = "/api";
    static final String COMPLETED_ORDER = "/owner/completedOrder/{forId}";
    static final String FIND_ALL_PLACES = "/owner/findAllPlaces";
    static final String FIND_ALL_OWNERS = "/owner/findAllOwners";
    static final String CREATE_PLACE = "/owner/createPlace";

    private final PlaceMapper placeMapper;
    private final PlaceService placeService;
    private final OwnerMapper ownerMapper;
    private final OwnerService ownerService;
    private final FoodOrderingRequestService foodOrderingRequestService;

    @PutMapping(value = COMPLETED_ORDER)
    public ResponseEntity completedStatusOrder(
            @PathVariable("forId") Integer forId
    ) {
        if (Objects.isNull(forId)) {
            return ResponseEntity.notFound().build();
        }
        foodOrderingRequestService.completed(forId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = FIND_ALL_PLACES)
    public List<PlaceDTO> findAllPlaces() {
        return placeService.findAll().stream()
                .map(placeMapper::map)
                .toList();
    }

    @GetMapping(value = FIND_ALL_OWNERS)
    public List<OwnerDTO> findAllOwners() {
        return ownerService.findAll().stream()
                .map(ownerMapper::map)
                .toList();
    }

    @PostMapping(value = CREATE_PLACE)
    public ResponseEntity createPlace(
            @Valid @RequestBody PlaceDTO placeDTO
    ) {
        placeService.placeCreate(placeMapper.map(placeDTO)
                .withOwner(ownerService.findOwner(placeDTO.getEmail())));
        return ResponseEntity.ok().build();
    }

}
