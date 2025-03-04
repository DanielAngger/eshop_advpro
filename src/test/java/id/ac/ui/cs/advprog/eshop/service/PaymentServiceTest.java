package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {
    private PaymentRepository paymentRepository;
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentRepository = mock(PaymentRepository.class);
        paymentService = new PaymentService(paymentRepository);
    }

    @Test
    void testCreatePaymentShouldSaveAndReturnPayment() {
        Map<String, String> paymentData = new HashMap<>();
        Payment payment = new Payment("P001", "VOUCHER", OrderStatus.WAITING_PAYMENT, paymentData);

        // Tidak perlu `when(paymentRepository.save())` karena save() bertipe void
        doNothing().when(paymentRepository).save(payment);

        Payment result = paymentService.createPayment(payment);

        assertNotNull(result);
        assertEquals("P001", result.getId());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testFindPaymentByIdShouldReturnCorrectPayment() {
        Payment payment = new Payment("P001", "VOUCHER", OrderStatus.WAITING_PAYMENT, new HashMap<>());
        when(paymentRepository.findById("P001")).thenReturn(Optional.of(payment));

        Optional<Payment> result = paymentService.findPaymentById("P001");

        assertTrue(result.isPresent());
        assertEquals("P001", result.get().getId());
    }

    @Test
    void testFindAllPaymentsShouldReturnAllStoredPayments() {
        List<Payment> payments = List.of(
                new Payment("P001", "VOUCHER", OrderStatus.WAITING_PAYMENT, new HashMap<>()),
                new Payment("P002", "CASH_ON_DELIVERY", OrderStatus.WAITING_PAYMENT, new HashMap<>())
        );
        when(paymentRepository.findAll()).thenReturn(payments);

        Collection<Payment> result = paymentService.findAllPayments();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(payments));
    }

    @Test
    void testDeletePaymentByIdShouldThrowExceptionIfStatusIsNotWaitingPayment() {
        Payment payment = new Payment("P001", "VOUCHER", OrderStatus.SUCCESS, new HashMap<>()); // Status bukan WAITING_PAYMENT

        when(paymentRepository.findById("P001")).thenReturn(Optional.of(payment));

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            paymentService.deletePaymentById("P001");
        });

        assertEquals("Payment hanya bisa dihapus jika statusnya WAITING_PAYMENT.", exception.getMessage());
        verify(paymentRepository, never()).deleteById("P001"); // Pastikan delete tidak dipanggil
    }

    @Test
    void testDeletePaymentByIdShouldWorkIfStatusIsWaitingPayment() {
        Payment payment = new Payment("P002", "VOUCHER", OrderStatus.WAITING_PAYMENT, new HashMap<>());

        when(paymentRepository.findById("P002")).thenReturn(Optional.of(payment));
        doNothing().when(paymentRepository).deleteById("P002");

        paymentService.deletePaymentById("P002");

        verify(paymentRepository, times(1)).deleteById("P002");
    }
}