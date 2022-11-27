package com.example.hospitalmanagementsystembackend.advice;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleExceptionOccurrence(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException
                .getBindingResult()
                .getFieldErrors()
                .forEach((fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage())));

        return errors;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(BAD_REQUEST)
    public Map<String, String> handleCustomException(RuntimeException exception) {
        HashMap<String, String> exceptionWithMessage = new HashMap<>();
        exceptionWithMessage.put("error", exception.getMessage());

        return exceptionWithMessage;
    }
}
