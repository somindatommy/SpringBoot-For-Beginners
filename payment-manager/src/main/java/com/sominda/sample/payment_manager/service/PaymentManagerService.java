package com.sominda.sample.payment_manager.service;

import com.sominda.sample.payment_manager.model.Payment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentManagerService {

    private final Map<String, Payment> database = new HashMap<>();

    public void initiatePayment(Payment payment) {

        database.put(payment.getPaymentId(), payment);
    }

    public Payment getPaymentByID(String paymentId) {

        return database.get(paymentId);
    }

    public List<Payment> getAllPayments() {

        return new ArrayList<>(database.values());
    }
}
