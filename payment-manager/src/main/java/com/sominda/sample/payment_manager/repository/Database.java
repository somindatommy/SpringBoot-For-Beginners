package com.sominda.sample.payment_manager.repository;

import com.sominda.sample.payment_manager.model.Payment;
import com.sominda.sample.payment_manager.model.Status;

import java.util.List;

public interface Database {

    void addPayment(Payment payment);

    Payment getPaymentById(String paymentId);

    List<Payment> getAllPayments();

    default List<Payment> getAllPaymentsByStatus(Status status) {

        throw new UnsupportedOperationException("This operation is not implemented for this db type.");
    }
}
