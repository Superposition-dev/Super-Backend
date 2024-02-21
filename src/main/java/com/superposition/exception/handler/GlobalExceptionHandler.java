package com.superposition.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.superposition.exception.SuperpositionException;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SuperpositionException.class)
    protected ResponseEntity<?> handleSuperpositionException(HttpServletRequest request, SuperpositionException e) {
        log.error("exception at = {}", e.getStackTrace()[0]);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(e.getErrorCode().getHttpStatus().value())
                .error(e.getErrorCode().getHttpStatus().name())
                .code(e.getErrorCode().name())
                .message(e.getErrorCode().getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }

    @Getter
    public static class ErrorResponse {
        private final LocalDateTime timestamp = LocalDateTime.now();
        private final int status;
        private final String error;
        private final String code;
        private final String message;
        private final String path;

        @JsonInclude(Include.NON_EMPTY)
        private final List<ValidationError> errors;

        @Builder
        public ErrorResponse(int status, String error, String code, String message, String path,
                             List<ValidationError> errors) {
            this.status = status;
            this.error = error;
            this.code = code;
            this.message = message;
            this.path = path;
            this.errors = errors;
        }
    }

    @Getter
    public static class ValidationError {
        private final String field;
        private final String message;

        @Builder
        public ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public static ValidationError of(FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}
