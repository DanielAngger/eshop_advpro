package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

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
        ModelAndView modelAndView = new ModelAndView("order/pay");
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @PostMapping("/pay/{orderId}")
    public ModelAndView processPayment(@PathVariable String orderId) {
        Order updatedOrder = orderService.updateStatus(orderId, "PAID");
        ModelAndView modelAndView = new ModelAndView("order/payment-success");
        modelAndView.addObject("paymentId", updatedOrder.getId());
        return modelAndView;
    }
}
