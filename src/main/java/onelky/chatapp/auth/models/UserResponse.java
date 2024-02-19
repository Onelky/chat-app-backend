package onelky.chatapp.auth.models;

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
    String profilePicture;

    public static UserResponse convertUserToUserResponse(User user){
        return UserResponse
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .profilePicture(user.getProfilePictureUrl())
                .build();
    }
}
