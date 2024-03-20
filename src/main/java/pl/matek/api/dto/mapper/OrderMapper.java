package pl.matek.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.matek.api.dto.OrderDTO;
import pl.matek.domain.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    //todo
    OrderDTO map(Order order);
}
