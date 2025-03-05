package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

class PaymentTest {

    @Test
    void testCreatePaymentSuccessfully() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("1", "VOUCHER", OrderStatus.WAITING_PAYMENT, paymentData);

        assertEquals("1", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals(OrderStatus.WAITING_PAYMENT, payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testInvalidVoucherCodeShouldBeRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "MEOW");

        Payment payment = new Payment("2", "VOUCHER", OrderStatus.WAITING_PAYMENT, paymentData);
        payment.validateVoucher();

        assertEquals(OrderStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testValidVoucherCodeShouldBeSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678"); // valid

        Payment payment = new Payment("1", "VOUCHER", OrderStatus.WAITING_PAYMENT, paymentData);
        payment.validateVoucher();

        assertEquals(OrderStatus.SUCCESS, payment.getStatus());
    }

    @Test
    void testInvalidVoucherCodeShouldBeRejectedDueToShortLength() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123"); // kurang dari 16 karakter

        Payment payment = new Payment("2", "VOUCHER", OrderStatus.WAITING_PAYMENT, paymentData);
        payment.validateVoucher();

        assertEquals(OrderStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testInvalidVoucherCodeShouldBeRejectedDueToMissingESHOPPrefix() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "SHOP1234ABC5678"); // tidak diawali "ESHOP"

        Payment payment = new Payment("3", "VOUCHER", OrderStatus.WAITING_PAYMENT, paymentData);
        payment.validateVoucher();

        assertEquals(OrderStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testInvalidVoucherCodeShouldBeRejectedDueToLackOfNumbers() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPABCDEFGH1234"); // hanya 4 angka

        Payment payment = new Payment("4", "VOUCHER", OrderStatus.WAITING_PAYMENT, paymentData);
        payment.validateVoucher();

        assertEquals(OrderStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testValidCashOnDeliveryPaymentShouldBeSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Raya No. 123");
        paymentData.put("deliveryFee", "5000");

        Payment payment = new Payment("5", "CASH_ON_DELIVERY", OrderStatus.WAITING_PAYMENT, paymentData);
        payment.validateCashOnDelivery();

        assertEquals(OrderStatus.SUCCESS, payment.getStatus());
    }

    @Test
    void testCashOnDeliveryShouldBeRejectedIfAddressIsEmpty() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", ""); // alamat kosong
        paymentData.put("deliveryFee", "5000");

        Payment payment = new Payment("6", "CASH_ON_DELIVERY", OrderStatus.WAITING_PAYMENT, paymentData);
        payment.validateCashOnDelivery();

        assertEquals(OrderStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testCashOnDeliveryShouldBeRejectedIfDeliveryFeeIsEmpty() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Raya No. 123");
        paymentData.put("deliveryFee", ""); // fee kosong

        Payment payment = new Payment("7", "CASH_ON_DELIVERY", OrderStatus.WAITING_PAYMENT, paymentData);
        payment.validateCashOnDelivery();

        assertEquals(OrderStatus.REJECTED, payment.getStatus());
    }
}