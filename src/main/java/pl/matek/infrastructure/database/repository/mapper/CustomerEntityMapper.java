package pl.matek.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.matek.domain.Customer;
import pl.matek.infrastructure.database.entity.CustomerEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {

    @Mapping (target = "addressCustomer.placeEntities", ignore = true)
    @Mapping (target = "addressCustomer.customerEntities", ignore = true)
    @Mapping(target = "foodOrderingRequestEntities", ignore = true)
    Customer mapFromEntity(CustomerEntity customerEntity);
}
