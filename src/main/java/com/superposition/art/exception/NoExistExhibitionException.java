package com.superposition.art.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoExistExhibitionException extends RuntimeException {

    private static final String MESSAGE = "해당하는 게시물이 없습니다.";

    public NoExistExhibitionException(String message) {
        super(message);
    }

    public NoExistExhibitionException() {
        super(MESSAGE);
    }
}
