package pl.matek.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.matek.domain.Place;
import pl.matek.infrastructure.database.entity.PlaceEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlaceEntityMapper {

    @Mapping(target = "owner.placeEntities", ignore = true)
    @Mapping(target = "productEntities", ignore = true)
    @Mapping(target = "deliveryAddressEntities", ignore = true)
    @Mapping(target = "addressPlace.placeEntities", ignore = true)
    @Mapping(target = "addressPlace.customerEntities", ignore = true)
    Place mapFromEntity(PlaceEntity placeEntity);

    PlaceEntity mapToEntity(Place place);
}
