package com.sominda.sample.payment_manager.model.api;

public record PaymentRequest (String initiator, String receiver, double amount) { }
