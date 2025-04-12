package com.sominda.payments.payment_manager_server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Payment")
@Table(name = "payments")
public class Payment {

    @Id
    @Column(name = "payment_id", unique = true, nullable = false)
    private String paymentId;

    @Column(name = "initiator_id", nullable = false)
    private String initiatorId;

    @Column(name = "receiver_id", nullable = false)
    private String receiverId;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "initiated_time", nullable = false)
    private LocalDateTime initiatedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
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
