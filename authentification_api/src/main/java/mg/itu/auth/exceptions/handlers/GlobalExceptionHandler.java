package mg.itu.auth.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import mg.itu.auth.exceptions.EmailAlreadyUsedException;
import mg.itu.auth.exceptions.InvalidValidationCodeException;
import mg.itu.auth.exceptions.responses.ApiErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Handle email already used (custom error code: 1000)
    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(EmailAlreadyUsedException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(400, 1000, ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Handle invalid validation code (custom error code: 2000)
    @ExceptionHandler(InvalidValidationCodeException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(InvalidValidationCodeException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(400, 2000, ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}