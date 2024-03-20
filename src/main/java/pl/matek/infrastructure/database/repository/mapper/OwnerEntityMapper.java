package pl.matek.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.matek.domain.Owner;
import pl.matek.infrastructure.database.entity.OwnerEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OwnerEntityMapper {
    OwnerEntity mapToEntity(Owner owner);

    @Mapping(target = "placeEntities", ignore = true)
    Owner mapFromEntity(OwnerEntity saved);
}
