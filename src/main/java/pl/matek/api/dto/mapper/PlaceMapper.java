package pl.matek.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.matek.api.dto.PlaceDTO;
import pl.matek.domain.Address;
import pl.matek.domain.Place;

@Mapper(componentModel = "spring")
public interface PlaceMapper {

    @Mapping(source = "addressPlace.country", target = "country")
    @Mapping(source = "addressPlace.postcode", target = "postcode")
    @Mapping(source = "addressPlace.street", target = "street")
    @Mapping(source = "addressPlace.streetNumber", target = "streetNumber")
    @Mapping(target = "email", ignore = true)
    PlaceDTO map(Place place);

    default Place map(PlaceDTO placeDTO) {
        return Place.builder()
                .phone(placeDTO.getPhone())
                .name(placeDTO.getName())
                .addressPlace(Address.builder()
                        .country(placeDTO.getCountry())
                        .postcode(placeDTO.getPostcode())
                        .street(placeDTO.getStreet())
                        .streetNumber(placeDTO.getStreetNumber())
                        .build())
                .build();
    }
}
