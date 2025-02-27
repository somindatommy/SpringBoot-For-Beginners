package com.sominda.sample.payment_manager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private String paymentId;
    private String initiatorId;
    private String receiverId;
    private double amount;
    private LocalDateTime initiatedTime;
    private Status status;

    private static final AtomicLong COUNTER = new AtomicLong(1);

    // This is to create a Payment object. This method will also generate a uuid for each object.
    public static Payment create(String initiatorId, String receiverId, double amount) {

        LocalDateTime now = LocalDateTime.now();
        String paymentId = generatePaymentId(now, initiatorId);
        return new Payment(paymentId, initiatorId, receiverId, amount, now, Status.PENDING);
    }

    private static String generatePaymentId(LocalDateTime dateTime, String initiatorId) {

        String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        long counter = COUNTER.getAndIncrement();
        return String.format("PAY-%s-%s-%05d", formattedDate, initiatorId.toUpperCase(), counter);
    }
}
