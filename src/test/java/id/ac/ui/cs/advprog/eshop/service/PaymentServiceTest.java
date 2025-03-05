package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order order;
    private Payment payment;
    private Map<String, String> paymentData;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb5589-1c39-460e-8860-71afbaf63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        this.products.add(product1);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b", this.products, 1708560000L, "Safira Sudrajat");
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123ABC45678");

        payment = new Payment("1", "VOUCHER", OrderStatus.WAITING_PAYMENT, paymentData);
    }

    @Test
    void testAddPayment() {
        doNothing().when(paymentRepository).save(any(Payment.class));
        Payment createdPayment = paymentService.addPayment(order, "VOUCHER", paymentData);

        assertNotNull(createdPayment);
        assertEquals("VOUCHER", createdPayment.getMethod());
        assertEquals(OrderStatus.WAITING_PAYMENT, createdPayment.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusSuccess() {
        doNothing().when(paymentRepository).save(any(Payment.class));
        when(orderRepository.findById(payment.getId())).thenReturn(order);

        Payment updatedPayment = paymentService.setStatus(payment, "SUCCESS");

        assertEquals(OrderStatus.SUCCESS, updatedPayment.getStatus());
        verify(paymentRepository, times(1)).save(payment);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testSetStatusRejected() {
        doNothing().when(paymentRepository).save(any(Payment.class));
        when(orderRepository.findById(payment.getId())).thenReturn(order);

        Payment updatedPayment = paymentService.setStatus(payment, "REJECTED");

        assertEquals(OrderStatus.REJECTED, updatedPayment.getStatus());
        verify(paymentRepository, times(1)).save(payment);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testGetPayment() {
        when(paymentRepository.findById("1")).thenReturn(Optional.of(payment));
        Payment retrievedPayment = paymentService.getPayment("1");

        assertNotNull(retrievedPayment);
        assertEquals("1", retrievedPayment.getId());
        verify(paymentRepository, times(1)).findById("1");
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = Arrays.asList(payment);
        when(paymentRepository.findAll()).thenReturn(payments);

        Collection<Payment> retrievedPayments = paymentService.getAllPayments();

        assertEquals(1, retrievedPayments.size());
        verify(paymentRepository, times(1)).findAll();
    }


    @Test
    void testProcessPaymentWithValidVoucher() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP123ABC45678"); // Valid voucher

        Payment payment = new Payment("P001", "VOUCHER", OrderStatus.WAITING_PAYMENT, paymentData);
        when(paymentRepository.findById("P001")).thenReturn(Optional.of(payment));

        paymentService.processPayment("P001");

        assertEquals(OrderStatus.SUCCESS, payment.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testProcessPaymentWithInvalidVoucher() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALID1234"); // Invalid voucher

        Payment payment = new Payment("P002", "VOUCHER", OrderStatus.WAITING_PAYMENT, paymentData);
        when(paymentRepository.findById("P002")).thenReturn(Optional.of(payment));

        paymentService.processPayment("P002");

        assertEquals(OrderStatus.REJECTED, payment.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testProcessPaymentWithValidCashOnDelivery() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Merdeka No.10");
        paymentData.put("deliveryFee", "5000");

        Payment payment = new Payment("P003", "CASH_ON_DELIVERY", OrderStatus.WAITING_PAYMENT, paymentData);
        when(paymentRepository.findById("P003")).thenReturn(Optional.of(payment));

        paymentService.processPayment("P003");

        assertEquals(OrderStatus.SUCCESS, payment.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testProcessPaymentWithEmptyAddressShouldBeRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", ""); // Alamat kosong
        paymentData.put("deliveryFee", "5000");

        Payment payment = new Payment("P004", "CASH_ON_DELIVERY", OrderStatus.WAITING_PAYMENT, paymentData);
        when(paymentRepository.findById("P004")).thenReturn(Optional.of(payment));

        paymentService.processPayment("P004");

        assertEquals(OrderStatus.REJECTED, payment.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testProcessPaymentWithEmptyDeliveryFeeShouldBeRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Merdeka No.10");
        paymentData.put("deliveryFee", ""); // Ongkir kosong

        Payment payment = new Payment("P005", "CASH_ON_DELIVERY", OrderStatus.WAITING_PAYMENT, paymentData);
        when(paymentRepository.findById("P005")).thenReturn(Optional.of(payment));

        paymentService.processPayment("P005");

        assertEquals(OrderStatus.REJECTED, payment.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }
}