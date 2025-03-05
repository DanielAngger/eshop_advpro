package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/detail")
    public String showPaymentDetailForm() {
        return "payment/detail";
    }

    @GetMapping("/detail/{paymentId}")
    public ModelAndView showPaymentDetail(@PathVariable String paymentId) {
        Payment payment = paymentService.getPayment(paymentId);
        if (payment == null) {
            return new ModelAndView("error").addObject("message", "Payment not found");
        }
        return new ModelAndView("payment/detail").addObject("payment", payment);
    }

    @GetMapping("/admin/list")
    public ModelAndView showAllPayments() {
        ModelAndView modelAndView = new ModelAndView("payment/admin/list");
        modelAndView.addObject("payments", paymentService.getAllPayments());
        return modelAndView;
    }

    @GetMapping("/admin/detail/{paymentId}")
    public ModelAndView showAdminPaymentDetail(@PathVariable String paymentId) {
        Payment payment = paymentService.getPayment(paymentId);
        ModelAndView modelAndView = new ModelAndView("payment/admin/detail");
        modelAndView.addObject("payment", payment);
        return modelAndView;
    }

    @PostMapping("/admin/set-status/{paymentId}")
    public ModelAndView setPaymentStatus(@PathVariable String paymentId, @RequestParam String status) {
        Payment payment = paymentService.getPayment(paymentId);
        paymentService.setStatus(payment, status);
        ModelAndView modelAndView = new ModelAndView("payment/admin/detail");
        modelAndView.addObject("payment", payment);
        return modelAndView;
    }
}
