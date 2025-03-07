package com.sominda.sample.payment_manager.controller;

import com.sominda.sample.payment_manager.exception.PaymentManagerServerException;
import com.sominda.sample.payment_manager.model.Payment;
import com.sominda.sample.payment_manager.model.api.PaymentInitiatorResponse;
import com.sominda.sample.payment_manager.model.api.PaymentRequest;
import com.sominda.sample.payment_manager.service.PaymentManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/status/{status}")
    public List<Payment> getPaymentsByStatus(@PathVariable String status) {

        return paymentManagerService.getPaymentsByStatus(status);
    }

    @GetMapping("/authdata")
    @ResponseStatus(HttpStatus.OK)
    public void getAuthentication() {

        // Print authenticated user details.
        printAuthenticatedUser();
    }

    private void printAuthenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new PaymentManagerServerException("02", "Unable to find authentication data", "Server failed to " +
                    "find user information for the authenticated user");
        }
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            throw new PaymentManagerServerException("03", "Unable to find authenticated principal", "Server " +
                    "failed to find user information for the authenticated user");
        }
        // Since this is a JWT, we can map it to jwt object type.
        Map<String, Object> claims = ((Jwt) principal).getClaims();
        if (CollectionUtils.isEmpty(claims)) {
            throw new PaymentManagerServerException("04", "Unable to authenticated user claims", "Server obtain " +
                    "authenticated user claims");
        }
        System.out.printf("Authenticated user: %s, %s, %s, %s%n", claims.get("sid"),claims.get(
                "username"), claims.get("given_name"),claims.get("email"));
    }
}
