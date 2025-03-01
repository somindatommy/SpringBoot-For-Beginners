package com.sominda.sample.payment_manager.exception;

import lombok.Getter;

/**
 * This will be the parent type of all the exceptions thrown from the service.
 */
@Getter
public class PaymentManagerException extends RuntimeException {

    private final String code;
    private final String description;

    public PaymentManagerException(String code, String message, String description) {

        super(message);
        this.code = code;
        this.description = description;
    }

    public PaymentManagerException(String code, String message, Throwable cause) {

        super(message, cause);
        this.code = code;
        this.description = message;
    }
}
