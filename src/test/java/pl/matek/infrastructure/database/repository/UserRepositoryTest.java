package pl.matek.infrastructure.database.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.domain.User;
import pl.matek.infrastructure.database.repository.mapper.UserEntityMapperImpl;
import pl.matek.infrastructure.security.RoleEntity;
import pl.matek.infrastructure.security.RoleRepository;
import pl.matek.infrastructure.security.UserEntity;
import pl.matek.infrastructure.security.UserJpaRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private UserEntityMapperImpl userEntityMapper;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserRepository userRepository;

    @Test
    void saveCreate() {
        //given
        UserEntity userEntity = UserEntity.builder()
                .email("email")
                .password("asddasadsdasdas")
                .active(false)
                .build();
        User user = User.builder()
                .email("email")
                .password("asddasadsdasdas")
                .active(false)
                .build();
        when(userEntityMapper.mapToEntity(any(User.class)))
                .thenReturn(new UserEntity());
        when(roleRepository.findByRole(anyString()))
                .thenReturn(new RoleEntity());
        when(userJpaRepository.save(any(UserEntity.class)))
                .thenReturn(userEntity);
        when(userEntityMapper.mapFromEntity(any(UserEntity.class)))
                .thenCallRealMethod();

        //when
        var result = userRepository.saveCreate("Role", user);

        //then
        assertNotNull(result);
        assertEquals(user, result);
    }
}