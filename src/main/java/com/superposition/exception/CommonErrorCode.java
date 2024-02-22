package com.superposition.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    FORBIDDEN(HttpStatus.FORBIDDEN, "No permission"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    CONFLICT(HttpStatus.CONFLICT, "duplication error")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
