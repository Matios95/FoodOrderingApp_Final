package pl.matek.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.matek.api.dto.FoodOrderingRequestDTO;
import pl.matek.domain.FoodOrderingRequest;

@Mapper(componentModel = "spring")
public interface FoodOrderingRequestMapper  extends OffsetDateTimeMapper {

    @Mapping(target = "customerName", source = "customer.name")
    @Mapping(target = "customerSurname", source = "customer.surname")
    @Mapping(target = "addressCountry", source = "customer.addressCustomer.country")
    @Mapping(target = "addressPostcode", source = "customer.addressCustomer.postcode")
    @Mapping(target = "addressStreet", source = "customer.addressCustomer.street")
    @Mapping(target = "addressStreetNumber", source = "customer.addressCustomer.streetNumber")
    @Mapping(target = "datetime", source = "datetime", qualifiedByName = "mapOffsetDateTimeToString")
    FoodOrderingRequestDTO map(FoodOrderingRequest foodOrderingRequest);
}
