package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    @Mock
    private Model model;

    private Payment mockPayment;
    private String paymentId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentId = UUID.randomUUID().toString();
        mockPayment = new Payment(paymentId, "VOUCHER", OrderStatus.WAITING_PAYMENT, Map.of("voucherCode", "ESHOP1234ABC5678"));
    }

    @Test
    void testShowPaymentDetailForm() {
        String viewName = paymentController.showPaymentDetailForm();
        assertEquals("payment/detail", viewName);
    }

    @Test
    void testShowPaymentDetailById() {
        when(paymentService.getPayment(paymentId)).thenReturn(mockPayment);
        ModelAndView modelAndView = paymentController.showPaymentDetail(paymentId);
        assertEquals("payment/detail", modelAndView.getViewName());
        assertEquals(mockPayment, modelAndView.getModel().get("payment"));
    }

    @Test
    void testShowAllPayments() {
        when(paymentService.getAllPayments()).thenReturn(Collections.singletonList(mockPayment));
        ModelAndView modelAndView = paymentController.showAllPayments();
        assertEquals("payment/admin/list", modelAndView.getViewName());
        assertEquals(1, ((Iterable<?>) modelAndView.getModel().get("payments")).spliterator().getExactSizeIfKnown());
    }

    @Test
    void testShowAdminPaymentDetail() {
        when(paymentService.getPayment(paymentId)).thenReturn(mockPayment);
        ModelAndView modelAndView = paymentController.showAdminPaymentDetail(paymentId);
        assertEquals("payment/admin/detail", modelAndView.getViewName());
        assertEquals(mockPayment, modelAndView.getModel().get("payment"));
    }

    @Test
    void testSetPaymentStatus() {
        when(paymentService.getPayment(paymentId)).thenReturn(mockPayment);
        when(paymentService.setStatus(mockPayment, "ACCEPTED")).thenReturn(mockPayment);
        ModelAndView modelAndView = paymentController.setPaymentStatus(paymentId, "ACCEPTED");
        assertEquals("payment/admin/detail", modelAndView.getViewName());
        verify(paymentService, times(1)).setStatus(mockPayment, "ACCEPTED");
    }
}
