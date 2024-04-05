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
public class PlaceDTO {

    private Integer placeId;

    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String phone;
    private String name;
    private String country;
    private String postcode;
    private String street;
    private Integer streetNumber;
}
