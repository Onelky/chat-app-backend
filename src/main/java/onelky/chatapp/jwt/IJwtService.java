package onelky.chatapp.jwt;

import onelky.chatapp.entities.user.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String getToken(User user);

    String getUsernameFromToken(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
