package pl.matek.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.matek.domain.Product;
import pl.matek.infrastructure.database.entity.ProductEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductEntityMapper {

    @Mapping(target = "orderEntities", ignore = true)
    @Mapping(target = "place", ignore = true)
    Product mapFromEntity(ProductEntity product);

    ProductEntity mapToEntity(Product product);
}
