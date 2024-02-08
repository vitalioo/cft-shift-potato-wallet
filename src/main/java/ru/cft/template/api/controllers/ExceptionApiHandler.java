package ru.cft.template.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.cft.template.api.exception.UserNotFoundException;
import ru.cft.template.api.model.ErrorResponse;

@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, exception.getLocalizedMessage());

        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
