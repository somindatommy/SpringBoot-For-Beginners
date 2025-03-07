package com.sominda.sample.swagger.payment_manager_swagger.controller;

import com.sominda.sample.swagger.payment_manager_swagger.api.PaymentsApi;
import com.sominda.sample.swagger.payment_manager_swagger.model.InitiatePaymentRequest;
import com.sominda.sample.swagger.payment_manager_swagger.model.Payment;
import com.sominda.sample.swagger.payment_manager_swagger.model.PaymentInitiatorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PaymentController implements PaymentsApi {

    @Override
    public ResponseEntity<List<Payment>> getAllPayments() {

        Payment payment = new Payment();
        payment.setPaymentId("1");
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Payment> getPaymentById(String paymentId) {

        Payment payment = new Payment();
        payment.setPaymentId("12");
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Payment>> getPaymentsByStatus(String status) {

        return getAllPayments();
    }

    @Override
    public ResponseEntity<PaymentInitiatorResponse> payUser(InitiatePaymentRequest initiatePaymentRequest) {

        // Logic to initiate the payment (mocked here for simplicity)
        PaymentInitiatorResponse response = new PaymentInitiatorResponse();

        // For example purposes, let's assume paymentId is generated and status is "PENDING"
        String paymentId = "PAY-" + System.currentTimeMillis();
        response.setPaymentId(paymentId);
        response.setStatus("PENDING");

        // You can replace the above logic with your actual payment initiation logic.

        // Returning the successful response with HTTP status 200
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
