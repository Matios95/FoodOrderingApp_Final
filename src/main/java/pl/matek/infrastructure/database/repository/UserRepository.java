package pl.matek.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.matek.business.dao.UserDAO;
import pl.matek.domain.User;
import pl.matek.infrastructure.database.repository.mapper.UserEntityMapper;
import pl.matek.infrastructure.security.RoleEntity;
import pl.matek.infrastructure.security.RoleRepository;
import pl.matek.infrastructure.security.UserEntity;
import pl.matek.infrastructure.security.UserJpaRepository;

import java.util.Set;

@Repository
@AllArgsConstructor
public class UserRepository implements UserDAO {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;
    private final RoleRepository roleRepository;

    @Override
    public User saveCreate(String role, User user) {

        UserEntity toSave = userEntityMapper.mapToEntity(user);
        RoleEntity roleEntity = roleRepository.findByRole(role);
        toSave.setRoles(Set.of(roleEntity));
        UserEntity saved = userJpaRepository.save(toSave);
        return userEntityMapper.mapFromEntity(saved);
    }
}
