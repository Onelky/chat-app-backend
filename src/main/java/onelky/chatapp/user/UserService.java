package onelky.chatapp.user;

import lombok.RequiredArgsConstructor;
import onelky.chatapp.cloudinary.CloudinaryService;
import onelky.chatapp.user.models.UpdateUserRequest;
import onelky.chatapp.user.models.UpdateUserResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;

    public UpdateUserResponse update(String username, Optional<UpdateUserRequest> updatedUser, MultipartFile profilePicture) throws IOException {
        User existingUser =  userRepository.findByUsername(username).orElseThrow();

        updatedUser.ifPresent(updateUserRequest -> updateUserProperties(updateUserRequest, existingUser));
        if (profilePicture != null) updateProfilePicture(existingUser, profilePicture);

        userRepository.save(existingUser);

        return UpdateUserResponse
                .builder()
                .username(existingUser.getUsername())
                .profilePicture(existingUser.getProfilePictureUrl())
                .email(existingUser.getEmail())
                .build();
    }

    private String getNewPassword(String newPassword){
        return passwordEncoder.encode(newPassword);
    }

    private String[] getNullPropertyNames(UpdateUserRequest user) {
        Set<String> nullFields = new HashSet<>();
        Field[] fields = user.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(user);
                if (value == null) {
                    nullFields.add(field.getName());
                }
            } catch (IllegalAccessException ignored) {
            }
        }

        return nullFields.toArray(new String[0]);

    }

    private void updateUserProperties(UpdateUserRequest updatedUser, User existingUser){
        String[] nullProperties = getNullPropertyNames(updatedUser);

        if (nullProperties.length == updatedUser.getClass().getDeclaredFields().length) throw new IllegalArgumentException ("Invalid body");

        BeanUtils.copyProperties(updatedUser, existingUser, nullProperties);

        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(getNewPassword(updatedUser.getPassword()));
        }

    }

    private void updateProfilePicture( User existingUser, MultipartFile profilePicture) throws IOException {
        HashMap<String, String> uploadedPicture = cloudinaryService.uploadFile(profilePicture);
        existingUser.setProfilePictureUrl(uploadedPicture.get("url"));
        existingUser.setProfilePicturePublicId(uploadedPicture.get("publicId"));
    }
}
