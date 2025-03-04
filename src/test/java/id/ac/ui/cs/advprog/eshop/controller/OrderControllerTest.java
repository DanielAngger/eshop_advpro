package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private Model model;

    @InjectMocks
    private OrderController orderController;

    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        sampleOrder = new Order("1", Collections.emptyList(), "2025-03-04", "Daniel", "PENDING");
    }

    @Test
    void testCreateOrderPage() {
        String viewName = orderController.createOrderPage();
        assertEquals("order/create", viewName);
    }

    @Test
    void testHistoryPage() {
        String viewName = orderController.historyPage();
        assertEquals("order/history", viewName);
    }

    @Test
    void testShowOrderHistory() {
        when(orderService.findAllByAuthor("Daniel")).thenReturn(Collections.singletonList(sampleOrder));

        String viewName = orderController.showOrderHistory("Daniel", model);

        verify(model).addAttribute(eq("orders"), anyList());
        assertEquals("order/history", viewName);
    }

    @Test
    void testShowPaymentPage() {
        when(orderService.findById("1")).thenReturn(sampleOrder);

        String viewName = orderController.showPaymentPage("1", model);

        verify(model).addAttribute("order", sampleOrder);
        assertEquals("order/pay", viewName);
    }

    @Test
    void testProcessPayment() {
        Map<String, String> paymentData = new HashMap<>();
        Payment mockPayment = new Payment("1", "VOUCHER", OrderStatus.WAITING_PAYMENT, paymentData);
        when(paymentService.createPayment(any())).thenReturn(mockPayment);

        String viewName = orderController.processPayment("1", model);

        verify(model).addAttribute("paymentId", mockPayment.getId());
        assertEquals("order/payment_success", viewName);
    }
}
