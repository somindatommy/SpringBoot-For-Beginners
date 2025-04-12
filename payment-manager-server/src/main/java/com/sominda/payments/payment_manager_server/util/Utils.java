package com.sominda.payments.payment_manager_server.util;

import com.sominda.payments.payment_manager_server.model.Payment;
import com.sominda.payments.server.generated.PaymentResponse;
import com.sominda.payments.server.generated.Status;

import java.time.format.DateTimeFormatter;

public class Utils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static PaymentResponse buildGrpcPaymentResponse(Payment payment) {
        // Format initiatedTime as string
        String formattedTime = payment.getInitiatedTime().format(formatter);

        return PaymentResponse.newBuilder()
                .setPaymentId(payment.getPaymentId())
                .setInitiatorId(payment.getInitiatorId())
                .setReceiverId(payment.getReceiverId())
                .setAmount(payment.getAmount())
                .setInitiatedTime(formattedTime)
                .setStatus(Status.valueOf(payment.getStatus().name()))
                .build();
    }

    public static com.sominda.payments.payment_manager_server.model.Status getStatus(Status status) {

        return com.sominda.payments.payment_manager_server.model.Status.valueOf(status.name());
    }
}
