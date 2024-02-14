package onelky.chatapp.user;

import lombok.RequiredArgsConstructor;
import onelky.chatapp.jwt.IJwtService;
import onelky.chatapp.user.models.UpdateUserRequest;
import onelky.chatapp.user.models.UpdateUserResponse;
import onelky.chatapp.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final IJwtService jwtService;
    private final UserService userService;

    @PutMapping(value = "/{username}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UpdateUserResponse> update(@RequestHeader("Authorization") String token,
                                                     @RequestPart(value = "user", required = false) UpdateUserRequest user,
                                                     @RequestPart(value = "profilePicture", required = false)
                                                     MultipartFile profilePicture, @PathVariable("username") String username) throws IOException {
        if (user == null && profilePicture == null)  throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid body");

       String tokenUsername = jwtService.getUsernameFromToken(Utils.getToken(token));
        if (!username.equals(tokenUsername)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized to perform operation");
        return ResponseEntity.ok(userService.update(username, Optional.ofNullable(user), profilePicture));
    }

}
