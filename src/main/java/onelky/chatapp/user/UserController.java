package onelky.chatapp.user;

import lombok.RequiredArgsConstructor;
import onelky.chatapp.user.models.UpdateUserRequest;
import onelky.chatapp.user.models.UpdateUserResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping(value = "/{username}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UpdateUserResponse> update(@RequestPart(value = "user", required = false) UpdateUserRequest user,
                                                     @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture,
                                                     @PathVariable("username") String username) throws IOException {
        return ResponseEntity.ok(userService.update(username, Optional.ofNullable(user), profilePicture));
    }

}
