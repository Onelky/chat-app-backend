package onelky.chatapp.user;

import onelky.chatapp.auth.IAuthService;
import onelky.chatapp.user.models.UpdateUserRequest;
import onelky.chatapp.user.models.UpdateUserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private IAuthService authService;



    @Test
    @WithMockUser(username = "test")
    public void whenUpdateUser_returnsUpdatedUser() throws IOException {
        User user = getMockedUser();
        when(userRepository.findByUsername(Mockito.any(String.class))).thenReturn(user);
        when(authService.isAuthorized("test")).thenReturn(true);

        UpdateUserRequest update = UpdateUserRequest
                .builder()
                .email("newEmail@gmail.com")
                .build();

        UpdateUserResponse updatedUser = userService.update("test", Optional.ofNullable(update), null);
        assertThat(updatedUser.getEmail()).isEqualTo("newEmail@gmail.com");
    }

    @Test()
    @WithMockUser(username = "test")
    public void whenUpdateUserWithInvalidAuthorization_thenThrowsIllegalArgumentException() {
        assertThrows(AccessDeniedException.class, () -> userService.update("test", Optional.empty(), null));
    }

    private User getMockedUser(){
        return User.builder()
                .email("test@gmail.com")
                .username("test")
                .provider(Provider.LOCAL)
                .password("testpassword")
                .build();
    }

}
