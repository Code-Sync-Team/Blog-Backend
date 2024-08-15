package code_sync_team.blog.global.auth.exception;

import code_sync_team.blog.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {
    LOGIN_IS_REQUIRED("로그인이 필요합니다", HttpStatus.FORBIDDEN),

    ;

    private final String message;
    private final HttpStatus status;

    AuthErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public HttpStatus defaultHttpStatus() {
        return status;
    }

    @Override
    public String defaultMessage() {
        return message;
    }

    @Override
    public RuntimeException defaultException() {
        return new CustomAuthException(this);
    }

    @Override
    public RuntimeException defaultException(Throwable cause) {
        return null;
    }
}
