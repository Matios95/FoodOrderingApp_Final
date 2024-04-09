package pl.matek.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.matek.business.dao.UserDAO;
import pl.matek.domain.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @Test
    void userCreate_returnVoid() {
        //given
        String role = "owner";
        User user = User.builder()
                .email("zajavka@email.com")
                .password("asads12312331ads")
                .active(true)
                .build();
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<String> captorString = ArgumentCaptor.forClass(String.class);

        //when
        userService.userCreate(role, user);

        //then
        verify(userDAO).saveCreate(captorString.capture(), captor.capture());
        assertEquals(role, captorString.getValue());
        assertEquals(user, captor.getValue());
    }

}