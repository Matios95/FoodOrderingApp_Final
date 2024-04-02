package pl.matek.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;
import pl.matek.api.dto.MenuDTO;
import pl.matek.api.dto.ProductDTO;
import pl.matek.domain.Product;
import pl.matek.domain.exception.NotFoundException;
import pl.matek.infrastructure.database.entity.ProductType;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "quantity", ignore = true)
    @Mapping(source = "type", target = "type", qualifiedByName = "StringMapFromEnum")
    MenuDTO mapMenu(Product product);

    @Named("StringMapFromEnum")
    default String StringMapFromEnum(ProductType productType)
    {
        return productType.toString();
    }

    @Mapping(target = "image", ignore = true)
    @Mapping(source = "image", target = "imageByte", qualifiedByName = "byteMapToString")
    @Mapping(source = "type", target = "type", qualifiedByName = "typeMapToEnum")
    ProductDTO map(Product product);

    @Named("byteMapToString")
    default String byteMapToString(byte[] bytes) {
        if (Objects.isNull(bytes))
            return "";
        return String.format("data:image/png;base64,%s",
                Base64.getEncoder().encodeToString(bytes));
    }

    @Named("typeMapToEnum")
    default List<String> typeMapToEnum(ProductType productType) {
        return List.of(productType.toString());
    }

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "place", ignore = true)
    @Mapping(target = "orderEntities", ignore = true)
    @Mapping(source = "type", target = "type", qualifiedByName = "typeMapFromEnum")
    @Mapping(source = "image", target = "image", qualifiedByName = "multipartFileMapToByte")
    Product map(ProductDTO productDTO);

    @Named("multipartFileMapToByte")
    default byte[] multipartFileMapToByte(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            throw new NotFoundException("Image problem upload: [%s]".formatted(multipartFile.getName()));
        }
    }

    @Named("typeMapFromEnum")
    default ProductType typeMapFromEnum(List<String> type) {
        return ProductType.valueOf(type.get(0));
    }
}
