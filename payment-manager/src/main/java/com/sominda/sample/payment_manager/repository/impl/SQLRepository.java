package com.sominda.sample.payment_manager.repository.impl;

import com.sominda.sample.payment_manager.exception.PaymentManagerClientException;
import com.sominda.sample.payment_manager.model.Payment;
import com.sominda.sample.payment_manager.model.Status;
import com.sominda.sample.payment_manager.repository.Database;
import com.sominda.sample.payment_manager.repository.PaymentsJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Profile("MySQLRepo")
@Service
public class SQLRepository implements Database {

    @Autowired
    private PaymentsJPARepository paymentsJPARepository;

    @Override
    public void addPayment(Payment payment) {

        System.out.println("Adding payment from SQLRepository");
        paymentsJPARepository.save(payment);
    }

    @Override
    public Payment getPaymentById(String paymentId) {

        System.out.println("Fetching a payment from id SQLRepository");
        return paymentsJPARepository.findById(paymentId)
                .orElseThrow(() -> new PaymentManagerClientException("01", "No payment found", "No payment found " +
                        "with the given id: " + paymentId));
    }

    @Override
    public List<Payment> getAllPayments() {

        System.out.println("Fetching all payment from SQLRepository");
        return paymentsJPARepository.findAll();
    }

    @Override
    public List<Payment> getAllPaymentsByStatus(Status status) {

        System.out.println("Fetching all payments by status from SQLRepository");
        return paymentsJPARepository.findAllPaymentsByStatus(status);
    }

    private List<Payment> getAllPaymentsWithPagination(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("initiatedTime").descending());
        Page<Payment> paymentPage = paymentsJPARepository.findAll(pageable);
        return paymentPage.getContent(); // Get the list of payments from the Page object
    }
}
