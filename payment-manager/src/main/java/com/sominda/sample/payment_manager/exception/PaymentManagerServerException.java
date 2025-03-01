package com.sominda.sample.payment_manager.exception;

/**
 * Thrown for internal server error which were unexpected.
 */
public class PaymentManagerServerException extends PaymentManagerException{

    public PaymentManagerServerException(String code, String message, String description) {

        super(code, message, description);
    }

    public PaymentManagerServerException(String code, String message, Throwable cause) {

        super(code, message, cause);
    }

    @Override
    public String getCode() {

        // Append a prefix to indicate this is a client exception starting with 500xxx.
        // PME stands for payment manager exception.
        return "PME-500" + super.getCode();
    }
}
