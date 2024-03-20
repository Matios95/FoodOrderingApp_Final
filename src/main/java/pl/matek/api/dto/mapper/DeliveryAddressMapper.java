package pl.matek.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.matek.api.dto.DeliveryAddressDTO;
import pl.matek.domain.DeliveryAddress;

@Mapper(componentModel = "spring")
public interface DeliveryAddressMapper {

    DeliveryAddressDTO map(DeliveryAddress deliveryAddress);

    @Mapping(target = "deliveryAddressId", ignore = true)
    @Mapping(target = "placeDeliveryAddress", ignore = true)
    DeliveryAddress map(DeliveryAddressDTO deliveryAddressDTO);
}
