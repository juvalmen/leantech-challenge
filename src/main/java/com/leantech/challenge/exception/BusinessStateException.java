package com.leantech.challenge.exception;

import lombok.Data;

@Data
public class BusinessStateException extends RuntimeException {

    public BusinessStateException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BusinessStateException(final String message) {
        super(message);
    }

}