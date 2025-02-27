package com.sominda.sample.payment_manager.controller;

import com.sominda.sample.payment_manager.model.Payment;
import com.sominda.sample.payment_manager.model.api.PaymentInitiatorResponse;
import com.sominda.sample.payment_manager.model.api.PaymentRequest;
import com.sominda.sample.payment_manager.service.PaymentManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    PaymentManagerService paymentManagerService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<Payment> getAllPayment() {

        return paymentManagerService.getAllPayments();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable String id) {

        return paymentManagerService.getPaymentByID(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public PaymentInitiatorResponse payUser(@RequestBody PaymentRequest paymentRequest) {

        Payment payment = Payment.create(paymentRequest.initiator(), paymentRequest.receiver(),
                paymentRequest.amount());
        paymentManagerService.initiatePayment(payment);
        return new PaymentInitiatorResponse(payment.getPaymentId(), payment.getStatus());
    }
}
