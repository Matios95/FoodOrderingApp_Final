package pl.matek.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.matek.domain.DeliveryAddress;
import pl.matek.infrastructure.database.entity.DeliveryAddressEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeliveryAddressEntityMapper {

    @Mapping(target = "placeDeliveryAddress", ignore = true)
//    @Mapping(target = "placed.owner", ignore = true)
//    @Mapping(target = "placed.addressPlace", ignore = true)
//    @Mapping(target = "placed.productEntities", ignore = true)
//    @Mapping(target = "placed.deliveryAddressEntities", ignore = true)
    DeliveryAddress mapFromEntity(DeliveryAddressEntity deliveryAddressEntity);

    @Mapping(target = "placed.placeId", source = "placeDeliveryAddress.placeId")
    DeliveryAddressEntity mapToEntity(DeliveryAddress deliveryAddress);
}
