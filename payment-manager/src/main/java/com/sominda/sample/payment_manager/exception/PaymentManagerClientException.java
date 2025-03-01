package com.sominda.sample.payment_manager.exception;

/**
 * Thrown for client errors.
 */
public class PaymentManagerClientException extends PaymentManagerException {

    public PaymentManagerClientException(String code, String message, String description) {

        super(code, message, description);
    }

    public PaymentManagerClientException(String code, String message, Throwable cause) {

        super(code, message, cause);
    }

    @Override
    public String getCode() {

        // Append a prefix to indicate this is a client exception starting with 140xxx.
        // PME stands for payment manager exception.
        return "PME-140" + super.getCode();
    }
}
