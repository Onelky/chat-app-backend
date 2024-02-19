package onelky.chatapp.auth;

import onelky.chatapp.auth.models.AuthResponse;
import onelky.chatapp.auth.models.LoginRequest;
import onelky.chatapp.auth.models.RegisterRequest;

public interface IAuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
    boolean isAuthorized(String username);

}
