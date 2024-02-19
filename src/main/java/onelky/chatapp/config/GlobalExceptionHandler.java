package onelky.chatapp.config;

import onelky.chatapp.common.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(new ErrorResponse(ex.getReason()));
    }

    @ExceptionHandler({BadCredentialsException.class, AccessDeniedException.class})
    public ResponseEntity<ErrorResponse> handleInvalidRequestException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Unauthorized"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeException(HttpMediaTypeException ex) {
        return ResponseEntity.status(ex.getBody().getStatus()).body(new ErrorResponse("Invalid request body"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("An unexpected error occurred"));
    }
}
