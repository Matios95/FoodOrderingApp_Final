package pl.matek.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.matek.business.dao.UserDAO;
import pl.matek.domain.User;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserDAO userDAO;

    public void userCreate(String role, User user) {
        userDAO.saveCreate(role, user);
    }
}
