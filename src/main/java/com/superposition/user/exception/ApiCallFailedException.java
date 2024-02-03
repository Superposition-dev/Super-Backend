package com.superposition.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "API Call Failed")
public class ApiCallFailedException extends RuntimeException{
}
