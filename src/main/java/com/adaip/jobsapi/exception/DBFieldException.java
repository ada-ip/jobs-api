package com.adaip.jobsapi.exception;

public class DBFieldException extends RuntimeException {
    private final String field;
    private final String message;

    public DBFieldException(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
