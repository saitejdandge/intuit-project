package com.intuit.app.exception_filter;

import com.intuit.app.Constants;
import com.intuit.app.exceptions.BaseException;
import com.intuit.app.exceptions.ErrorCodes;
import com.intuit.app.exceptions.ResponseStatus;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException runtimeException) {
        ErrorResponse errorResponse = getErrorResponse(runtimeException);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse getErrorResponse(RuntimeException e) {
        e.printStackTrace();
        if (e instanceof BaseException) {
            BaseException baseException = (BaseException) e;
            if (baseException.getErrorCode() != null)
                return new ErrorResponse(baseException.getErrorCode());
            else
                return new ErrorResponse(baseException.getMessage(), baseException.getStatusCode(), baseException.getOpStatus());
        } else
            return new ErrorResponse(ErrorCodes.STANDARD_ERROR);
    }

    @Data
    private static class ErrorResponse {
        private final String message;
        private final int opStatus, statusCode;
        private final ZonedDateTime timestamp;
        private final ResponseStatus status;
        private final int result;

        public ErrorResponse(ErrorCodes errorCode) {
            this(errorCode.getMessage(), errorCode.getStatusCode(), errorCode.getOpStatus());
        }

        public ErrorResponse(String message, int statusCode, int opStatus) {
            this.timestamp = ZonedDateTime.now(ZoneId.of(Constants.TIMEZONE));
            this.message = message;
            this.statusCode = statusCode;
            this.opStatus = opStatus;
            this.status = ResponseStatus.FAILED;
            this.result = 0;
        }

    }

}
