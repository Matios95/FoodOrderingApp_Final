package pl.matek.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.matek.api.dto.MenuDTO;
import pl.matek.api.dto.OrderDTO;
import pl.matek.domain.Order;
import pl.matek.infrastructure.database.entity.ProductType;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productType", source = "product.type")
    @Mapping(target = "price", source = "product.price")
    OrderDTO map(Order order);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "orderaId", ignore = true)
    @Mapping(target = "orderaCode", ignore = true)
    @Mapping(target = "foodOrderingRequestEntity", ignore = true)
    Order map(MenuDTO menuDTO);
}
