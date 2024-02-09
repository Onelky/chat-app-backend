package onelky.chatapp.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UpdateUserRequest update(int id, UpdateUserRequest updatedUser) {
        User existingUser =  userRepository.findById(id).orElseThrow();
        String[] nullProperties = getNullPropertyNames(updatedUser);

        if (nullProperties.length == updatedUser.getClass().getDeclaredFields().length) throw new IllegalArgumentException ("Invalid body");

        BeanUtils.copyProperties(updatedUser, existingUser, nullProperties);

        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(getNewPassword(updatedUser.getPassword()));
        }

        userRepository.save(existingUser);
        return UpdateUserRequest
                .builder()
                .username(existingUser.getUsername())
                .profilePicture(existingUser.getProfilePicture())
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
}
