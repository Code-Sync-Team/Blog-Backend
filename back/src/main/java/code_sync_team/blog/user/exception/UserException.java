package code_sync_team.blog.user.exception;

import code_sync_team.blog.global.exception.CustomException;
import code_sync_team.blog.global.exception.ErrorCode;

public class UserException extends CustomException {
    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UserException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }



}
