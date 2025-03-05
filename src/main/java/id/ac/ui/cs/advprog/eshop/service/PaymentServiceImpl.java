package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(order.getId(), method, OrderStatus.WAITING_PAYMENT, paymentData);
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        OrderStatus newStatus = OrderStatus.valueOf(status);
        payment.setStatus(newStatus);
        paymentRepository.save(payment);

        // Update status order sesuai dengan status payment
        Order order = orderRepository.findById(payment.getId());
        if (order != null) {
            if (newStatus == OrderStatus.SUCCESS) {
                order = new Order(order.getId(), order.getProducts(), order.getOrderTime(), order.getAuthor(), "SUCCESS");
            } else if (newStatus == OrderStatus.REJECTED) {
                order = new Order(order.getId(), order.getProducts(), order.getOrderTime(), order.getAuthor(), "FAILED");
            }
            orderRepository.save(order);
        }

        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment tidak ditemukan."));
    }

    @Override
    public Collection<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }


    public void processPayment(String id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment tidak ditemukan."));

        switch (payment.getMethod()) {
            case "VOUCHER":
                payment.validateVoucher();
                break;
            case "CASH_ON_DELIVERY":
                validateCashOnDelivery(payment);
                break;
            default:
                throw new IllegalArgumentException("Metode pembayaran tidak valid.");
        }

        paymentRepository.save(payment);
    }

    private void validateCashOnDelivery(Payment payment) {
        if (payment.isValidCashOnDelivery()) {
            payment.setStatus(OrderStatus.SUCCESS);
        } else {
            payment.setStatus(OrderStatus.REJECTED);
        }
    }
}
