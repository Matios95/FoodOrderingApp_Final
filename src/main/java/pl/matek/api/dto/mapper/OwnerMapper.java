package pl.matek.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.matek.api.dto.OwnerDTO;
import pl.matek.domain.Owner;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    OwnerDTO map(Owner owner);
}
