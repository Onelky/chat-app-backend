package onelky.chatapp.user;

import onelky.chatapp.user.models.UpdateUserRequest;
import onelky.chatapp.user.models.UpdateUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        User user = User.builder()
                .email("test@gmail.com")
                .username("test")
                .provider(Provider.LOCAL)
                .password("testpassword")
                .build();
        when(userRepository.findByUsername(Mockito.any(String.class))).thenReturn(user);
    }

    @Test
    public void userService_update_returnsUpdateUserResponse() throws IOException {

        UpdateUserRequest update = UpdateUserRequest
                .builder()
                .email("newEmail@gmail.com")
                .build();

        UpdateUserResponse updatedUser = userService.update("test", Optional.ofNullable(update), null);
        assertThat(updatedUser.getEmail()).isEqualTo("newEmail@gmail.com");
    }
}
