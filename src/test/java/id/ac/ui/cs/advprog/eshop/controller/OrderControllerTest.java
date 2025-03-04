package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testShowCreateOrderPage() {
        String viewName = orderController.showCreateOrderPage(model);
        assertEquals("CreateOrder", viewName);
    }

    @Test
    void testShowOrderHistoryPage() {
        String viewName = orderController.showOrderHistoryPage(model);
        assertEquals("OrderHistory", viewName);
    }

    @Test
    void testShowOrdersByAuthor_WithExistingOrders() {
        List<Order> orders = List.of(new Order(1L, "John Doe", "Item A", 2));
        when(orderService.getOrdersByAuthor("John Doe")).thenReturn(orders);

        String viewName = orderController.showOrdersByAuthor("John Doe", model);

        verify(model).addAttribute("orders", orders);
        assertEquals("OrderList", viewName);
    }

    @Test
    void testShowOrdersByAuthor_NoOrdersFound() {
        when(orderService.getOrdersByAuthor("Unknown")).thenReturn(Collections.emptyList());

        String viewName = orderController.showOrdersByAuthor("Unknown", model);

        verify(model).addAttribute("orders", Collections.emptyList());
        assertEquals("OrderList", viewName);
    }

    @Test
    void testShowPaymentPage_WithValidOrder() {
        Order order = new Order(1L, "John Doe", "Item A", 2);
        when(orderService.getOrderById(1L)).thenReturn(order);

        String viewName = orderController.showPaymentPage(1L, model);

        verify(model).addAttribute("order", order);
        assertEquals("OrderPayment", viewName);
    }

    @Test
    void testShowPaymentPage_OrderNotFound() {
        when(orderService.getOrderById(99L)).thenReturn(null);

        String viewName = orderController.showPaymentPage(99L, model);

        verify(model).addAttribute("error", "Order not found!");
        assertEquals("OrderPayment", viewName);
    }

    @Test
    void testProcessPayment_Success() {
        when(orderService.processPayment(1L)).thenReturn("PAYMENT-123");

        String viewName = orderController.processPayment(1L, model);

        verify(model).addAttribute("paymentId", "PAYMENT-123");
        assertEquals("PaymentSuccess", viewName);
    }

    @Test
    void testProcessPayment_Failure() {
        when(orderService.processPayment(2L)).thenReturn(null);

        String viewName = orderController.processPayment(2L, model);

        verify(model).addAttribute("error", "Payment failed!");
        assertEquals("OrderPayment", viewName);
    }
}