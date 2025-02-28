package com.sominda.sample.payment_manager.repository.impl;

import com.sominda.sample.payment_manager.model.Payment;
import com.sominda.sample.payment_manager.repository.Database;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("MyBasicRepo")
@Repository("MapDatabaseRepository")
public class MapDatabaseRepository implements Database {

    private final Map<String, Payment> database = new HashMap<>();

    @Override
    public void addPayment(Payment payment) {

        System.out.println("addPayment Using the repository in MapDatabaseRepository");
        database.put(payment.getPaymentId(), payment);
    }

    @Override
    public Payment getPaymentById(String paymentId) {

        System.out.println("getPaymentById Using the repository in MapDatabaseRepository");
        return database.get(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {

        System.out.println("getAllPayments Using the repository in MapDatabaseRepository");
        return new ArrayList<>(database.values());
    }
}
