package pl.matek.api.controller.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.matek.api.dto.FoodOrderingRequestDTO;
import pl.matek.api.dto.OrderDTO;
import pl.matek.api.dto.TallyDTO;
import pl.matek.api.dto.mapper.FoodOrderingRequestMapper;
import pl.matek.api.dto.mapper.OrderMapper;
import pl.matek.business.FoodOrderingRequestService;
import pl.matek.business.OrderService;
import pl.matek.domain.FoodOrderingRequest;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping(CustomerRestController.API)
public class CustomerRestController {

    static final String API = "/api";
    static final String TALLY_INFO = "/customer/tally/{forId}/info";
    static final String TALLY_ALL_ACTIVE = "/customer/tallyAllActive";
    static final String TALLY_DELETE = "/customer/tally/{forId}/delete";

    static final Long MAX_MINUTE_DELETE_ORDER = 20L;

    private final FoodOrderingRequestService foodOrderingRequestService;
    private final FoodOrderingRequestMapper foodOrderingRequestMapper;
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping(value = TALLY_INFO)
    public ResponseEntity<TallyDTO> tallyInfo(
            @PathVariable("forId") Integer forId
    ) {
        if (Objects.isNull(forId)) {
            return ResponseEntity.notFound().build();
        }
        FoodOrderingRequest foodOrderingRequest = foodOrderingRequestService.findById(forId);
        FoodOrderingRequestDTO foodOrderingRequestDTO = foodOrderingRequestMapper.map(foodOrderingRequest);
        List<OrderDTO> orderDTOs = orderService.findByFoodOrderingRequest(foodOrderingRequest).stream().map(orderMapper::map).toList();
        return ResponseEntity
                .ok(TallyDTO.builder()
                        .foodOrderingRequestDTO(foodOrderingRequestDTO)
                        .listOrders(orderDTOs)
                        .build());
    }

    @GetMapping(value = TALLY_ALL_ACTIVE)
    public ResponseEntity<List<FoodOrderingRequestDTO>> tallAllActiveList() {
        List<FoodOrderingRequestDTO> list = foodOrderingRequestService.findAllActive().stream()
                .map(foodOrderingRequestMapper::map)
                .toList();
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = TALLY_DELETE)
    public ResponseEntity tallyDelete(
            @PathVariable("forId") Integer forId
    ) {
        FoodOrderingRequest foodOrderingRequest = foodOrderingRequestService.findById(forId);
        OffsetDateTime orderTime = foodOrderingRequest.getDatetime();
        OffsetDateTime maxTimeDeleteOrder = orderTime.plusMinutes(MAX_MINUTE_DELETE_ORDER);
        if (maxTimeDeleteOrder.isBefore(OffsetDateTime.now())) {
            return ResponseEntity.status(HttpStatusCode.valueOf(409)).build();
        }
        foodOrderingRequestService.delete(forId);
        return ResponseEntity.ok().build();
    }
}
