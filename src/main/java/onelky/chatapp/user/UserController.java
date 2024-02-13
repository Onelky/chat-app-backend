package onelky.chatapp.user;

import lombok.RequiredArgsConstructor;
import onelky.chatapp.common.entities.ErrorResponse;
import onelky.chatapp.user.models.UpdateUserRequest;
import onelky.chatapp.user.models.UpdateUserResponse;
import org.springframework.http.HttpStatus;
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
    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UpdateUserResponse> update(@RequestHeader("Authorization") String authorization,
                                                     @RequestPart(value = "user", required = false) UpdateUserRequest user,
                                                     @RequestPart(value = "profilePicture", required = false)
                                                     MultipartFile profilePicture, @PathVariable("id") int id) throws IOException {

        if (user == null && profilePicture == null) throw new IllegalArgumentException("Invalid user or profile picture");
        return ResponseEntity.ok(userService.update(id, Optional.ofNullable(user), profilePicture));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()) );
    }

}
