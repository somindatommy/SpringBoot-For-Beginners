package com.sominda.sample.payment_manager.repository;

import com.sominda.sample.payment_manager.model.Payment;
import com.sominda.sample.payment_manager.model.Status;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Profile("MySQLRepo")
@Repository
public interface PaymentsJPARepository extends JpaRepository<Payment, String> {

    @Transactional(readOnly = true)
    @Query("SELECT p FROM Payment p WHERE p.status = :status")
    List<Payment> findAllPaymentsByStatus(Status status);
}
