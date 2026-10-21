package com.ccsw.tutorial.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleCustomerAlreadyExistsException(CustomerAlreadyExistsException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(LoanValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleLoanValidationException(LoanValidationException exception) {
        return exception.getMessage();
    }
}
