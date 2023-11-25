package com.superposition.artist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "잘못된 요청입니다")
public class BadRequestException extends RuntimeException {
}
