package pl.matek.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.matek.api.dto.CustomerRegisterDTO;
import pl.matek.domain.CustomerRegister;

@Mapper(componentModel = "spring")
public interface CustomerRegisterMapper {
    CustomerRegister map(CustomerRegisterDTO customerRegisterDTO);
}
