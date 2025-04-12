package com.sominda.payments.payment_manager_server.repository;

import com.sominda.payments.payment_manager_server.model.Payment;
import com.sominda.payments.payment_manager_server.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PaymentsJPARepository extends JpaRepository<Payment, String> {

    @Transactional(readOnly = true)
    @Query("SELECT p FROM Payment p WHERE p.status = :status")
    List<Payment> findAllPaymentsByStatus(Status status);
}
