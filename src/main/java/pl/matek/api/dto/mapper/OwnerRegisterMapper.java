package pl.matek.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.matek.api.dto.OwnerRegisterDTO;
import pl.matek.domain.OwnerRegister;

@Mapper(componentModel = "spring")
public interface OwnerRegisterMapper {
    OwnerRegister map(OwnerRegisterDTO ownerRegisterDTO);
}
