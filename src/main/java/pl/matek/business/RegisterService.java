package pl.matek.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.matek.domain.Owner;
import pl.matek.domain.OwnerRegister;
import pl.matek.domain.User;

@Service
@AllArgsConstructor
public class RegisterService {

    private final OwnerService ownerService;
    private final UserService userService;

    @Transactional
    public void ownerCreate(OwnerRegister ownerRegister) {
        User user = userService.userCreate(User.builder()
                .email(ownerRegister.getEmail())
                .password(ownerRegister.getPassword())
                .active(true)
                .build());
        Owner owner = ownerService.ownerCreate(Owner.builder()
                .name(ownerRegister.getName())
                .surname(ownerRegister.getSurname())
                .email(ownerRegister.getEmail())
                .build());
    }
}
