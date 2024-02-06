package onelky.chatapp.auth;

import onelky.chatapp.entities.user.Provider;
import onelky.chatapp.entities.user.User;
import onelky.chatapp.entities.user.UserRepository;
import onelky.chatapp.jwt.IJwtService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final IJwtService jwtService;

    public AuthService(UserRepository userRepository, IJwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public AuthResponse login(LoginRequest request) {
        return null;
    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .provider(Provider.LOCAL)
                .build();
        userRepository.save(user);
        return AuthResponse.builder().token(jwtService.getToken(user)).build();
    }
}
