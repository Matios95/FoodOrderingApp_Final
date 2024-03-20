package pl.matek.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String productCode;
    private List<String> type;
    private String name;
    private String description;
    //todo walidacja ceny
    private BigDecimal price;
    private String imageByte;
    private MultipartFile image;
}
