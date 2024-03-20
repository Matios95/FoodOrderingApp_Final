package pl.matek.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.matek.api.dto.OwnerDTO;
import pl.matek.api.dto.OwnerRegisterDTO;
import pl.matek.domain.Owner;
import pl.matek.domain.OwnerRegister;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    OwnerDTO map(Owner owner);
}
