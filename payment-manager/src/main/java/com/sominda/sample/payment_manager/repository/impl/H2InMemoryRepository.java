package com.sominda.sample.payment_manager.repository.impl;

import com.sominda.sample.payment_manager.model.Payment;
import com.sominda.sample.payment_manager.model.Status;
import com.sominda.sample.payment_manager.repository.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Profile("H2Repo")
@Repository("H2InMemoryRepository")
public class H2InMemoryRepository implements Database {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public void addPayment(Payment payment) {

        String sql = "INSERT INTO PAYMENTS (PAYMENT_ID, INITIATOR_ID, RECEIVER_ID, AMOUNT, INITIATED_TIME, STATUS) " +
                "VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(sql, payment.getPaymentId(), payment.getInitiatorId(), payment.getReceiverId(),
                payment.getAmount(), payment.getInitiatedTime(),payment.getStatus().toString());
    }

    private static Payment rowMapper(ResultSet rs, int rowId) throws SQLException {

        Timestamp timestamp = rs.getTimestamp("INITIATED_TIME");
        LocalDateTime initiatedTime = timestamp != null ? timestamp.toLocalDateTime() : null;

        String statusStr = rs.getString("STATUS");
        Status status = statusStr == null ? null : Status.valueOf(statusStr);

        return new Payment(rs.getString("PAYMENT_ID"),
                rs.getString("INITIATOR_ID"),
                rs.getString("RECEIVER_ID"),
                rs.getDouble("AMOUNT"),
                initiatedTime,
                status
        );
    }

    @Override
    public Payment getPaymentById(String paymentId) {

        String sql = "SELECT * FROM PAYMENTS WHERE PAYMENT_ID = ?";
        Payment payment = null;
        try {
            payment = jdbcTemplate.queryForObject(sql, H2InMemoryRepository::rowMapper, paymentId);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No payment found with id " + paymentId);
        }
        return payment;
    }

    @Override
    public List<Payment> getAllPayments() {

        String sql = "SELECT * FROM PAYMENTS";
        List<Payment> payments = null;
        try {
            payments = jdbcTemplate.query(sql, H2InMemoryRepository::rowMapper);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No payment founds");
        }
        return payments;
    }
}
