package pl.matek.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.matek.domain.FoodOrderingRequest;
import pl.matek.infrastructure.database.entity.FoodOrderingRequestEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FoodOrderingRequestEntityMapper {

    @Mapping(target = "orderEntities", ignore = true)
    @Mapping(target = "customer.foodOrderingRequestEntities", ignore = true)
    @Mapping(target = "customer.addressCustomer.placeEntities", ignore = true)
    @Mapping(target = "customer.addressCustomer.customerEntities", ignore = true)
    FoodOrderingRequest mapFromEntity(FoodOrderingRequestEntity foodOrderingRequest);

    @Mapping(target = "orderEntities", ignore = true)
    @Mapping(target = "customer.foodOrderingRequestEntities", ignore = true)
    @Mapping(target = "customer.addressCustomer", ignore = true)
    FoodOrderingRequestEntity mapToEntity(FoodOrderingRequest foodOrderingRequest);
}
