package com.sominda.payments.payment_manager_server.service;

import com.sominda.payments.server.generated.GetAllPaymentsRequest;
import com.sominda.payments.server.generated.GetPaymentByIdRequest;
import com.sominda.payments.server.generated.GetPaymentsByStatusRequest;
import com.sominda.payments.server.generated.GetPaymentsResponse;
import com.sominda.payments.server.generated.InitiatePaymentResponse;
import com.sominda.payments.server.generated.PaymentRequest;
import com.sominda.payments.server.generated.PaymentResponse;
import com.sominda.payments.server.generated.PaymentServerGrpc;
import com.sominda.payments.payment_manager_server.model.Payment;
import com.sominda.payments.payment_manager_server.repository.PaymentsJPARepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sominda.payments.payment_manager_server.util.PaymentMapper.toProto;

@GrpcService
public class PaymentServerImpl extends PaymentServerGrpc.PaymentServerImplBase {

    @Autowired
    private PaymentsJPARepository paymentsJPARepository;

    @Override
    public void getPayments(GetAllPaymentsRequest request, StreamObserver<GetPaymentsResponse> responseObserver) {

        List<Payment> payments = paymentsJPARepository.findAll();
        List<PaymentResponse> paymentResponses = new ArrayList<>();

        for (Payment payment : payments) {
            paymentResponses.add(toProto(payment));
        }
        GetPaymentsResponse.Builder builder = GetPaymentsResponse.newBuilder();
        builder.addAllPayments(paymentResponses);

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getPaymentById(GetPaymentByIdRequest request, StreamObserver<PaymentResponse> responseObserver) {

        Optional<Payment> payments = paymentsJPARepository.findById(request.getPaymentId());
        if (payments.isPresent()) {
            responseObserver.onNext(toProto(payments.get()));
            responseObserver.onCompleted();
        }
        // Handle error.
    }

    @Override
    public void getPaymentsByStatus(GetPaymentsByStatusRequest request,
                                    StreamObserver<GetPaymentsResponse> responseObserver) {

        List<Payment> payments = paymentsJPARepository.findAllPaymentsByStatus(request.getStatus());
        List<PaymentResponse> paymentResponses = new ArrayList<>();

        for (Payment payment : payments) {
            paymentResponses.add(toProto(payment));
        }
        GetPaymentsResponse.Builder builder = GetPaymentsResponse.newBuilder();
        builder.addAllPayments(paymentResponses);

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void initiatePayment(PaymentRequest request, StreamObserver<InitiatePaymentResponse> responseObserver) {

        Payment payment = Payment.create(request.getInitiatorId(), request.getReceiverId(), request.getAmount());
        paymentsJPARepository.save(payment);
        InitiatePaymentResponse.Builder builder = InitiatePaymentResponse.newBuilder();
        builder.setPaymentId(payment.getPaymentId());
        builder.setStatus(payment.getStatus());
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
