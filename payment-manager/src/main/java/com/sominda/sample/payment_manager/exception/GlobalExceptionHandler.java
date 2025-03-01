package com.sominda.sample.payment_manager.exception;

import com.sominda.sample.payment_manager.model.api.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentManagerClientException.class)
    public ResponseEntity<ErrorResponse> handleClientExceptions(PaymentManagerClientException ex) {

        ErrorResponse error = new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getDescription());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentManagerServerException.class)
    public ResponseEntity<ErrorResponse> handleServerExceptions(PaymentManagerServerException ex) {

        ErrorResponse error = new ErrorResponse(ex.getCode(), ex.getMessage(), ex.getDescription());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralExceptions(Exception ex) {

        System.out.println("UNEXPECTED EXCEPTION: " + ex.getMessage());
        ex.printStackTrace();
        ErrorResponse error = new ErrorResponse("PME-50000", "Internal Server Error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
