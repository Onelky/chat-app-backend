package onelky.chatapp.auth;

import lombok.RequiredArgsConstructor;
import onelky.chatapp.auth.models.AuthResponse;
import onelky.chatapp.auth.models.LoginRequest;
import onelky.chatapp.auth.models.RegisterRequest;
import onelky.chatapp.auth.models.UserResponse;
import onelky.chatapp.user.Provider;
import onelky.chatapp.user.User;
import onelky.chatapp.user.UserRepository;
import onelky.chatapp.jwt.IJwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername());

        return AuthResponse
                .builder()
                .token(jwtService.getToken(user))
                .user(UserResponse.convertUserToUserResponse(user))
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .provider(Provider.LOCAL)
                .build();
        userRepository.save(user);
        return AuthResponse
                .builder()
                .token(jwtService.getToken(user))
                .user(UserResponse.convertUserToUserResponse(user))
                .build();
    }

    public boolean isAuthorized(String username){
        User loggedUser =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loggedUser.getUsername().equals(username);
    }
}
