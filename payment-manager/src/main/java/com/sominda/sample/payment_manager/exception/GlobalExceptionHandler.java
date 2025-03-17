package com.sominda.sample.payment_manager.exception;

import com.sominda.sample.payment_manager.model.api.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentManagerClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleClientExceptions(PaymentManagerClientException ex) {

        ErrorResponse error = new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getDescription());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentManagerServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleServerExceptions(PaymentManagerServerException ex) {

        ErrorResponse error = new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getDescription());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGeneralExceptions(Exception ex) {

        System.out.println("UNEXPECTED EXCEPTION: " + ex.getMessage());
        ex.printStackTrace();
        ErrorResponse error = new ErrorResponse("PME-50000", "Internal Server Error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
