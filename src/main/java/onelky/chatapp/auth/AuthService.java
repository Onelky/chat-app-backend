package onelky.chatapp.auth;

import lombok.RequiredArgsConstructor;
import onelky.chatapp.entities.user.Provider;
import onelky.chatapp.entities.user.User;
import onelky.chatapp.entities.user.UserRepository;
import onelky.chatapp.jwt.IJwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        return AuthResponse.builder().token(jwtService.getToken(user)).build();
    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .provider(Provider.LOCAL)
                .build();
        userRepository.save(user);
        return AuthResponse.builder().token(jwtService.getToken(user)).build();
    }
}
