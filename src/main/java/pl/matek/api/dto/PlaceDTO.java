package pl.matek.api.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTO {

    //todo telefon regex!!
    private Integer placeId;
    @Size(min = 2)
    private String phone;
    private String name;
    private String country;
    private String postcode;
    private String street;
    private Integer streetNumber;
}
