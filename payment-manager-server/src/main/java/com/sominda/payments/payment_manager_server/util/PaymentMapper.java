package com.sominda.payments.payment_manager_server.util;

import com.sominda.payments.payment_manager_server.model.Payment;
import com.sominda.payments.server.generated.PaymentResponse;
import com.sominda.payments.server.generated.Status;

import java.time.format.DateTimeFormatter;

/**
 * Class for mapping Payment object with PaymentResponse proto msg.
 */
public class PaymentMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * This method maps an instance of Payment to the PaymentResponse message.
     *
     * @param payment Instance of Payment.
     * @return Instance of PaymentResponse.
     */
    public static PaymentResponse toProto(Payment payment) {
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
}
