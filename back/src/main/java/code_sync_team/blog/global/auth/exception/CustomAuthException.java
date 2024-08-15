package code_sync_team.blog.global.auth.exception;

import code_sync_team.blog.global.exception.CustomException;
import code_sync_team.blog.global.exception.ErrorCode;

public class CustomAuthException extends CustomException {
    public CustomAuthException() { super(); }

    public CustomAuthException(String message) {
        super(message);
    }

    public CustomAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomAuthException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CustomAuthException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
