package pl.matek.business.dao;

import pl.matek.domain.User;

public interface UserDAO {
    User saveCreate(String role, User user);
}
