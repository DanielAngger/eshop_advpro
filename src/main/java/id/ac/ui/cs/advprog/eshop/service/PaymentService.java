package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface PaymentService {
    public Payment addPayment(Order order, String method, Map<String, String> paymentData);
    public Payment setStatus(Payment payment, String status);
    public Payment getPayment(String paymentId);
    public Collection<Payment> getAllPayments();
}