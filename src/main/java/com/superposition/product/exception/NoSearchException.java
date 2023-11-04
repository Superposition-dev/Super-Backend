package com.superposition.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSearchException extends RuntimeException{
    public NoSearchException(String message) {
        super(message);
    }
}
