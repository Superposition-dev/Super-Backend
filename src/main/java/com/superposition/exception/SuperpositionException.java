package com.superposition.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SuperpositionException extends RuntimeException {

    private final ErrorCode errorCode;
}
