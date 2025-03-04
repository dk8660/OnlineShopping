package com.example.onlineshopping.boundedContext.payment.controller;

import com.example.onlineshopping.base.rs.Rq;
import com.example.onlineshopping.base.rsData.RsData;
import com.example.onlineshopping.boundedContext.orders.entity.Orders;
import com.example.onlineshopping.boundedContext.orders.service.OrderService;
import com.example.onlineshopping.boundedContext.payment.enity.Payment;
import com.example.onlineshopping.boundedContext.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderService orderService;
    private final Rq rq;

    @PostMapping("/pay")
    String showPaymentForm(Model model, @RequestParam String id) {
        long orderId = Long.parseLong(id);
        Orders order = orderService.getOrderById(orderId);
        if(order == null) {
            return "/payment/404";
        }

        model.addAttribute("order", order);

        return "/payment/paymentForm";
    }

    @PostMapping("/pay/process")
    String confirmPayment(RedirectAttributes redirectAttributes,
                          @RequestParam String orderId,
                          @RequestParam String totalPrice,
                          @RequestParam String paymentMethod
                          ) {
        long orderid = Long.parseLong(orderId);
        Orders order = orderService.getOrderById(orderid);

        int totalprice = Integer.parseInt(totalPrice);

        Payment payment = paymentService.tryCreatePayment(orderid, totalprice, paymentMethod);
        redirectAttributes.addAttribute("paymentId", payment.getId());

        return "redirect:/pay/confirm";
    }

    @GetMapping("/pay/confirm")
    String confirmPayment(Model model, @RequestParam String paymentId) {
        long paymentid = Long.parseLong(paymentId);
        Payment payment = paymentService.getPaymentById(paymentid);
        model.addAttribute("payment", payment);
        return "/payment/confirm";
    }

    @PostMapping("/pay/complete")
    String completePayment(RedirectAttributes redirectAttributes, @RequestParam String paymentId) {
        long paymentid = Long.parseLong(paymentId);
        Payment payment = paymentService.getPaymentById(paymentid);

        RsData paymentRs = paymentService.tryUpdatePaymentStatus(paymentid);
        RsData orderRs = orderService.tryUpdateOrderStatus(payment.getOrderId());

        redirectAttributes.addAttribute("paymentId", paymentid);

        if(paymentRs.isSuccess() && orderRs.isSuccess()) {
            return "redirect:/pay/success";
        }
        else {
            return "redirect:/pay/fail";
        }
    }

    @GetMapping("/pay/success")
    String successPayment(Model model, @RequestParam long paymentId) {
        Payment payment = paymentService.getPaymentById(paymentId);
        model.addAttribute("payment", payment);
        return "/payment/success";
    }

    @GetMapping("/pay/fail")
    String failPayment(Model model, @RequestParam long paymentId) {
        Payment payment = paymentService.getPaymentById(paymentId);
        model.addAttribute("payment", payment);
        return "/payment/fail";
    }
}
