package com.sominda.sample.payment_manager.model.api;

import com.sominda.sample.payment_manager.model.Status;

public record PaymentInitiatorResponse(String paymentId, Status status) { }
