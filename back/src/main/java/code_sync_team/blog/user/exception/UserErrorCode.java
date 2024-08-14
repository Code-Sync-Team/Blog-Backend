package code_sync_team.blog.user.exception;

import code_sync_team.blog.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {
    USERNAME_ALREADY_EXISTS("이미 사용중인 계정입니다.", HttpStatus.CONFLICT),
    SIGN_UP_FAILED_DEFAULT("회원 가입을 다시 진행해주십시오.", HttpStatus.INTERNAL_SERVER_ERROR),
    MEMBER_NOT_FOUND("회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DEFAULT("회원 취급 중 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;

     UserErrorCode(String message, HttpStatus status) {
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
        return new UserException(this);
    }

    @Override
    public RuntimeException defaultException(Throwable cause) {
        return new UserException(this, cause);
    }
}
