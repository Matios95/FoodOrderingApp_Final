package pl.matek.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerRegisterDTO {

    private String name;
    private String surname;

    @Email
    private String email;

    @Size(min = 4)
    private String password;

    public static OwnerRegisterDTO buildDefaultData() {
        return OwnerRegisterDTO.builder()
                .name("Staszek")
                .surname("Kowalski")
                .email("staszek_kowalski@zajavka.pl")
                .password("test")
                .build();
    }
}
