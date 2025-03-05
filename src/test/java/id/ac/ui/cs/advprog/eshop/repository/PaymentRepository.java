package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
    }

    @Test
    void testSavePayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("1", "VOUCHER", OrderStatus.WAITING_PAYMENT, paymentData);
        paymentRepository.save(payment);

        Optional<Payment> retrievedPayment = paymentRepository.findById("1");
        assertTrue(retrievedPayment.isPresent());
        assertEquals(payment, retrievedPayment.get());
    }

    @Test
    void testFindPaymentByIdNotFound() {
        Optional<Payment> retrievedPayment = paymentRepository.findById("999");
        assertFalse(retrievedPayment.isPresent());
    }

    @Test
    void testDeletePayment() {
        Payment payment = new Payment("2", "CREDIT_CARD", OrderStatus.SUCCESS, new HashMap<>());
        paymentRepository.save(payment);

        paymentRepository.deleteById("2");

        Optional<Payment> retrievedPayment = paymentRepository.findById("2");
        assertFalse(retrievedPayment.isPresent());
    }

    @Test
    void testFindAllPayments() {
        Payment payment1 = new Payment("3", "BANK_TRANSFER", OrderStatus.WAITING_PAYMENT, new HashMap<>());
        Payment payment2 = new Payment("4", "EWALLET", OrderStatus.SUCCESS, new HashMap<>());

        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        assertEquals(2, paymentRepository.findAll().size());
    }
}