package code_sync_team.blog.image.exception;

import code_sync_team.blog.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ImageErrorCode implements ErrorCode {

    IS_NOT_IMAGE("이미지가 아닙니다.", HttpStatus.BAD_REQUEST),
    SERVER_ERROR("내부 에러가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;

    ImageErrorCode(String message, HttpStatus status) {
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
        return new ImageException(this);
    }

    @Override
    public RuntimeException defaultException(Throwable cause) {
        return new ImageException(this, cause);
    }
}
