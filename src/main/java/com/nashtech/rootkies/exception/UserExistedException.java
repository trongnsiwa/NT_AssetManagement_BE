package com.nashtech.rootkies.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserExistedException extends Exception {

    private static final long serialVersionUID = 1L;

    public UserExistedException(String message) {
        super(message);
    }

}