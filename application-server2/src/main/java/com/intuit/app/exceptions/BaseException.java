package com.intuit.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class BaseException extends RuntimeException {

    private String message;
    private int opStatus;
    private int statusCode;

    private ErrorCodes errorCode;

    public BaseException(ErrorCodes errorCode) {
        this.errorCode = errorCode;
    }

    public BaseException(String message, int opStatus, int statusCode) {
        this.message = message;
        this.opStatus = opStatus;
        this.statusCode = statusCode;
    }

}
