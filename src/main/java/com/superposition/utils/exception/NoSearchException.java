package com.superposition.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No Result By Keyword")
public class NoSearchException extends RuntimeException{
}
