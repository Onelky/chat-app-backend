package onelky.chatapp.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import onelky.chatapp.auth.IAuthService;
import onelky.chatapp.jwt.IJwtService;
import onelky.chatapp.user.models.UpdateUserRequest;
import onelky.chatapp.user.models.UpdateUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private IJwtService jwtService;

    @MockBean
    private IAuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private UpdateUserRequest user;

    @BeforeEach
    public void init(){
        user = UpdateUserRequest.builder().username("test").password("test").email("test@test.com").build();
    }

    @Test
    @WithMockUser(username = "test")
    public void whenUpdateUserWithValidInput_thenReturnsUpdatedUserResponse() throws Exception {
        UpdateUserResponse updateResponse = UpdateUserResponse.builder().username("updateTest").email("test@test.com").build();
        when(userService.update("test", Optional.ofNullable(user), null)).thenReturn(updateResponse);

        MockMultipartFile userData = getUserMock();
        mockMvc.perform(
                multipart("/user/test", "test")
                        .file(userData)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.username").value("updateTest"));
    }

    @Test
    @WithMockUser(username = "test")
    public void whenUpdateUserWithInValidInput_thenReturnsBadRequest() throws Exception {
        when(userService.update("test", Optional.empty(), null)).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(multipart("/user/test", "test")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test")
    public void whenUpdateUserWithInvalidCredentials_thenReturnsUnauthorized() throws Exception {
        MockMultipartFile userData = getUserMock();

        when(userService.update("test2", Optional.ofNullable(user), null)).thenThrow(AccessDeniedException.class);

        mockMvc.perform(multipart("/user/test2")
                        .file(userData)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    private MockMultipartFile getUserMock() throws JsonProcessingException {
        return new MockMultipartFile("user", "", "application/json", objectMapper.writeValueAsBytes(user));
    }

}
