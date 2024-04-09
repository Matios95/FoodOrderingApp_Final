package pl.matek.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegisterDTO {

    private String name;
    private String surname;
    private String country;
    private String postcode;
    private String street;
    private Integer streetNumber;

    @Email
    private String email;

    @Size(min = 4)
    private String password;

    
    public static CustomerRegisterDTO buildDefaultData() {
        return CustomerRegisterDTO.builder()
                .name("Zbyszek")
                .surname("Kupiec")
                .country("Polska")
                .postcode("32-010")
                .street("Kwiatowa")
                .streetNumber(777)
                .email("zbyszek_kupiec@zajavka.pl")
                .password("data")
                .build();
    }
}
