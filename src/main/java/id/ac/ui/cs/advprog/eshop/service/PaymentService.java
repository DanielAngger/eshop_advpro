package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import java.util.Collection;
import java.util.Optional;

public interface PaymentService {
    public Payment createPayment(Payment payment);
    public Optional<Payment> findPaymentById(String id);
    public Collection<Payment> findAllPayments();
    public void deletePaymentById(String id);
}