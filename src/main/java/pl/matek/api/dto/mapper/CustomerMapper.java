package pl.matek.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.matek.api.dto.FoodOrderingRequestDTO;
import pl.matek.domain.FoodOrderingRequest;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    FoodOrderingRequestDTO map(FoodOrderingRequest foodOrderingRequest);
}
