package onelky.chatapp.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onelky.chatapp.user.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    Integer id;
    String username;
    String email;
    String photoUrl;

    public static UserResponse convertUserToUserResponse(User user){
        return UserResponse
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .photoUrl(user.getProfilePicture())
                .build();
    }
}
