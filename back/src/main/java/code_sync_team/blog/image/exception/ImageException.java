package code_sync_team.blog.image.exception;

import code_sync_team.blog.global.exception.CustomException;
import code_sync_team.blog.global.exception.ErrorCode;

public class ImageException extends CustomException {

    public ImageException() {
        super();
    }

    public ImageException(String message) {
        super(message);
    }

    public ImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ImageException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
