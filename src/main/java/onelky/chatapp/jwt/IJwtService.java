package onelky.chatapp.jwt;

import onelky.chatapp.entities.user.User;

public interface IJwtService {
    String getToken(User user);
}
