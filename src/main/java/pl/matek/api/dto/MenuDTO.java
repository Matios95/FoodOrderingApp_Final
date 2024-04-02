package pl.matek.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {

   private String productCode;
   private String name;
   private String type;
   private String description;
   private BigDecimal price;
   private Integer quantity;
}
