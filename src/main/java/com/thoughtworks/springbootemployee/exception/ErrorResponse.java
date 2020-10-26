package com.thoughtworks.springbootemployee.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static java.time.LocalDateTime.now;

public class ErrorResponse {
    private final String errorCode;
    private final String errorMessage;
    private final int status;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime timestamp;

    public ErrorResponse(String errorCode, String errorMessage, int status) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = status;

        timestamp = now();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
