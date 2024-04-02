package pl.matek.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.matek.domain.Order;
import pl.matek.infrastructure.database.entity.OrderEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderEntityMapper {

    @Mapping(target = "product.orderEntities", ignore = true)
    @Mapping(target = "product.place", ignore = true)
    @Mapping(target = "foodOrderingRequestEntity.orderEntities", ignore = true)
    @Mapping(target = "foodOrderingRequestEntity.customer", ignore = true)
    Order mapFromEntity(OrderEntity orderEntity);

    OrderEntity mapToEntity(Order order);
}
