package com.superposition.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "해당 메서드에 대한 권한이 없습니다.")
public class ForbiddenException extends RuntimeException {
}
