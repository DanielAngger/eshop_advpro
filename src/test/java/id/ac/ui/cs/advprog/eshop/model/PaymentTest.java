package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

class PaymentTest {

    @Test
    void testCreatePaymentSuccessfully() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("1", "VOUCHER", "WAITING_PAYMENT", paymentData);

        assertEquals("1", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("WAITING_PAYMENT", payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testInvalidVoucherCodeShouldBeRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "MEOW");

        Payment payment = new Payment("2", "VOUCHER", "WAITING_PAYMENT", paymentData);
        payment.validateVoucher();

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testValidVoucherCodeShouldBeSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("3", "VOUCHER", "WAITING_PAYMENT", paymentData);
        payment.validateVoucher();

        assertEquals("SUCCESS", payment.getStatus());
    }
}