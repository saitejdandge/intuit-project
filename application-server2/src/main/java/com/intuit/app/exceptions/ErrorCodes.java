package com.intuit.app.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCodes {
    STANDARD_ERROR(404, 100, "Standard error code"),
    NO_CUSTOMER_FOUND(404, 101, "No Customer found"),
    INVALID_PRODUCT(404, 103, "Either product doesn't exist or not active"),
    PRODUCT_ALREADY_SUBSCRIBED(404, 102, "Product already subscribed"),
    SERVICE_ALREADY_RUNNING(424, 104, "Service name already exists"),
    PRODUCT_SERVICE_DOWN(424, 105, " Service is down");


    private final int statusCode;
    private final int opStatus;
    private final String message;

    public int getStatusCode() {
        return statusCode;
    }

    public int getOpStatus() {
        return opStatus;
    }

    public String getMessage() {
        return message;
    }
}
