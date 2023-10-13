package com.adaip.jobsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
            .getFieldErrors()
            .forEach(error -> {
                String field = error.getField();
                String message = error.getDefaultMessage();
                errors.put(field, message);
            });

        return errors;
    }

    @ExceptionHandler(UniqueFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleUniqueFieldError(UniqueFieldException ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ex.getField(), ex.getMessage());
        return error;
    }
}
