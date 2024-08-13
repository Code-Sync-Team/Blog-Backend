package code_sync_team.blog.global.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public final class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleException(CustomException exception) {
        ErrorResponse response = ErrorResponse.of(exception);
        HttpStatus httpStatus = exception.getErrorCode().defaultHttpStatus();
        return new ResponseEntity<>(response, httpStatus);
    }
}
