package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import java.util.Collection;
import java.util.Optional;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(Payment payment) {
        if (paymentRepository.findById(payment.getId()).isPresent()) {
            throw new IllegalArgumentException("Payment dengan ID ini sudah ada.");
        }
        paymentRepository.save(payment);
        return payment;
    }

    public Optional<Payment> findPaymentById(String id) {
        return paymentRepository.findById(id);
    }

    public Collection<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    public void deletePaymentById(String id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment tidak ditemukan."));

        if (payment.getStatus() != OrderStatus.WAITING_PAYMENT) {
            throw new IllegalStateException("Payment hanya bisa dihapus jika statusnya WAITING_PAYMENT.");
        }

        paymentRepository.deleteById(id);
    }
}
