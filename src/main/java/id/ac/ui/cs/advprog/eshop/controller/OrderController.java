package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/create")
    public String showCreateOrderPage() {
        return "order/create";
    }

    @GetMapping("/history")
    public String showOrderHistoryForm() {
        return "order/history";
    }

    @PostMapping("/history")
    public String getOrderHistory(@RequestParam String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        return "order/history";
    }

    @GetMapping("/pay/{orderId}")
    public ModelAndView showPaymentOrderPage(@PathVariable String orderId) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            return new ModelAndView("error").addObject("message", "Order not found");
        }

        ModelAndView modelAndView = new ModelAndView("order/pay");
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @PostMapping("/pay/{orderId}")
    public ModelAndView processPayment(@PathVariable String orderId) {
        try {
            Order updatedOrder = orderService.updateStatus(orderId, "PAID");
            ModelAndView modelAndView = new ModelAndView("order/payment-success");
            modelAndView.addObject("paymentId", updatedOrder.getId()); // Perbaikan: Menggunakan getId()
            return modelAndView;
        } catch (NoSuchElementException e) {
            return new ModelAndView("error").addObject("message", "Order not found or already paid");
        }
    }
}