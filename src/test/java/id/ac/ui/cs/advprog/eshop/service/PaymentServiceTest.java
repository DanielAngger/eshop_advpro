package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order order;
    private Payment payment;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb5589-1c39-460e-8860-71afbaf63bd6");
        product1. setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        Product product2 = new Product () ;
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        this.products.add(product1);
        this.products.add(product2);

        order = new Order("order1", this.products, 123456789L, "user1");
        payment = new Payment("payment1", "VOUCHER", OrderStatus.WAITING_PAYMENT, new HashMap<>());
    }

    @Test
    void testAddPayment() {
        doNothing().when(paymentRepository).save(any(Payment.class));
        Payment result = paymentService.addPayment(order, "VOUCHER", new HashMap<>());
        assertNotNull(result);
        assertEquals(OrderStatus.WAITING_PAYMENT, result.getStatus());
    }

    @Test
    void testSetStatusSuccess() {
        when(orderRepository.findById(payment.getId())).thenReturn(order);
        doNothing().when(paymentRepository).save(any(Payment.class));
        Payment result = paymentService.setStatus(payment, "SUCCESS");
        assertEquals(OrderStatus.SUCCESS, result.getStatus());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void testSetStatusRejected() {
        when(orderRepository.findById(payment.getId())).thenReturn(order);
        doNothing().when(paymentRepository).save(any(Payment.class));
        Payment result = paymentService.setStatus(payment, "REJECTED");
        assertEquals(OrderStatus.REJECTED, result.getStatus());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void testGetPaymentExists() {
        when(paymentRepository.findById("payment1")).thenReturn(Optional.of(payment));
        Payment result = paymentService.getPayment("payment1");
        assertEquals(payment, result);
    }

    @Test
    void testGetPaymentNotFound() {
        when(paymentRepository.findById("invalid"))
                .thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> paymentService.getPayment("invalid"));
    }

    @Test
    void testGetAllPayments() {
        Collection<Payment> payments = List.of(payment);
        when(paymentRepository.findAll()).thenReturn(payments);
        Collection<Payment> result = paymentService.getAllPayments();
        assertEquals(1, result.size());
    }

    @Test
    void testProcessPaymentVoucher() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment voucherPayment = new Payment("payment1", "VOUCHER", OrderStatus.WAITING_PAYMENT, paymentData);
        when(paymentRepository.findById("payment1")).thenReturn(Optional.of(voucherPayment));

        paymentService.processPayment("payment1");

        assertEquals(OrderStatus.SUCCESS, voucherPayment.getStatus());
        verify(paymentRepository).save(voucherPayment);
    }

    @Test
    void testProcessPaymentCashOnDelivery() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Test No. 1");
        paymentData.put("deliveryFee", "10000");
        Payment codPayment = new Payment("payment2", "CASH_ON_DELIVERY", OrderStatus.WAITING_PAYMENT, paymentData);
        when(paymentRepository.findById("payment2")).thenReturn(Optional.of(codPayment));

        paymentService.processPayment("payment2");

        assertEquals(OrderStatus.SUCCESS, codPayment.getStatus());
        verify(paymentRepository).save(codPayment);
    }

    @Test
    void testProcessPaymentInvalidMethod() {
        Payment invalidPayment = new Payment("payment3", "INVALID_METHOD", OrderStatus.WAITING_PAYMENT, new HashMap<>());
        when(paymentRepository.findById("payment3")).thenReturn(Optional.of(invalidPayment));

        assertThrows(IllegalArgumentException.class, () -> paymentService.processPayment("payment3"));
    }
}