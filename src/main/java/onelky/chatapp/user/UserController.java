package onelky.chatapp.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import onelky.chatapp.common.entities.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserRequest> update(@Valid @RequestBody UpdateUserRequest request, @PathVariable("id") int id){
        return ResponseEntity.ok(userService.update(id, request));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()) );
    }

}
