package code_sync_team.blog.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.Instant;


@Builder
public record ErrorResponse(
        String code,
        Integer status,
        String name,
        String message,
        Instant timestamp
) {
    public static ErrorResponse of(CustomException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        String errorName = exception.getClass().getName();
        errorName = errorName.substring(errorName.lastIndexOf('.') + 1);

        return ErrorResponse.builder()
                .code(errorCode.name())
                .status(errorCode.defaultHttpStatus().value())
                .name(errorName)
                .timestamp(Instant.now())
                .message(exception.getMessage())
                .build();
    }
}
