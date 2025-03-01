package com.sominda.sample.payment_manager.service;

import com.sominda.sample.payment_manager.model.Payment;
import com.sominda.sample.payment_manager.model.Status;
import com.sominda.sample.payment_manager.repository.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentManagerService {

    @Autowired
    Database database;

    public void initiatePayment(Payment payment) {

        database.addPayment(payment);
    }

    public Payment getPaymentByID(String paymentId) {

        return database.getPaymentById(paymentId);
    }

    public List<Payment> getAllPayments() {

        return database.getAllPayments();
    }

    public List<Payment> getPaymentsByStatus(String status) {

        return database.getAllPaymentsByStatus(Status.valueOf(status));
    }
}
