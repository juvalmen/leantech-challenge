package com.leantech.challenge.exception;

import lombok.Data;

@Data
public class ValidationException extends RuntimeException {

    public ValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ValidationException(final String message) {
        super(message);
    }

}
