package com.sominda.sample.payment_manager.repository;

import com.sominda.sample.payment_manager.model.Payment;

import java.util.List;

public interface Database {

    void addPayment(Payment payment);

    Payment getPaymentById(String paymentId);

    List<Payment> getAllPayments();
}
