package pl.matek.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodOrderingRequestDTO {

    private Integer foodOrderingRequestId;
    private String foodOrderingRequestCode;
    private String datetime;
    private String customerName;
    private String customerSurname;
    private String addressCountry;
    private String addressPostcode;
    private String addressStreet;
    private String addressStreetNumber;
    private Boolean completed;
}
