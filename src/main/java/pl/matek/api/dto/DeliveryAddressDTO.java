package pl.matek.api.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddressDTO {

    @Pattern(regexp = "^\\d{2}[- ]{0,1}\\d{3}$")
    private String postcode;
    private String street;
}
