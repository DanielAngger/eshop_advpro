package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private Model model;

    @InjectMocks
    private OrderController orderController;

    private Order order;

    private List<Product> products;

    @BeforeEach
    void setUp() {
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

        order = new Order ("13652556-012a-4c07-b546-54eb1396d79b", this.products,1708560000L,"Safira Sudrajat");
    }

    @Test
    void testShowCreateOrderPage() {
        String view = orderController.showCreateOrderPage();
        assertEquals("order/create", view);
    }

    @Test
    void testShowOrderHistoryForm() {
        String view = orderController.showOrderHistoryForm();
        assertEquals("order/history", view);
    }

    @Test
    void testGetOrderHistory() {
        when(orderService.findAllByAuthor("Daniel")).thenReturn(List.of(order));

        String view = orderController.getOrderHistory("Daniel", model);
        assertEquals("order/history", view);
        verify(model).addAttribute(eq("orders"), anyList());
    }

    @Test
    void testShowPaymentOrderPage() {
        when(orderService.findById("1")).thenReturn(order);

        ModelAndView modelAndView = orderController.showPaymentOrderPage("1");

        assertEquals("order/pay", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("order"));
    }

    @Test
    void testProcessPayment() {
        when(orderService.updateStatus("1", "PAID")).thenReturn(order);

        ModelAndView modelAndView = orderController.processPayment("1");

        assertEquals("order/payment-success", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("paymentId"));
    }
}