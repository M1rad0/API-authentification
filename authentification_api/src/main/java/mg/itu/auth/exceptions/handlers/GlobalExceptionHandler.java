package mg.itu.auth.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import mg.itu.auth.exceptions.CannotFindUserException;
import mg.itu.auth.exceptions.EmailAlreadyUsedException;
import mg.itu.auth.exceptions.ExpiredTokenException;
import mg.itu.auth.exceptions.IdentifiantAlreadyUsedException;
import mg.itu.auth.exceptions.InvalidLoginCodeException;
import mg.itu.auth.exceptions.InvalidTokenException;
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

    // Handle identifiant already used (same error code)
    @ExceptionHandler(IdentifiantAlreadyUsedException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(IdentifiantAlreadyUsedException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(400, 1000, ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Handle cannot find user already used (3000 error code)
    @ExceptionHandler(CannotFindUserException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(CannotFindUserException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(400, 3000, ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Handle invalid validation code (custom error code: 2000)
    @ExceptionHandler(InvalidValidationCodeException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(InvalidValidationCodeException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(400, 2000, ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Handle invalid token Exception (custom error code: 4001)
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(InvalidTokenException ex){
        ApiErrorResponse errorResponse = new ApiErrorResponse(400, 4001, ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Handle invalid token Exception (custom error code: 2001)
    @ExceptionHandler(InvalidLoginCodeException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(InvalidLoginCodeException ex){
        ApiErrorResponse errorResponse = new ApiErrorResponse(400, 2001, ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Handle expired token Exception (custom error code: 2002)
    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(ExpiredTokenException ex){
        ApiErrorResponse errorResponse = new ApiErrorResponse(400, 2001, ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}