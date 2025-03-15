package com.example.onlineshopping.boundedContext.orders.controller;

import com.example.onlineshopping.base.rs.Rq;
import com.example.onlineshopping.base.rsData.RsData;
import com.example.onlineshopping.boundedContext.orderItem.entity.OrderItem;
import com.example.onlineshopping.boundedContext.orderItem.service.OrderItemService;
import com.example.onlineshopping.boundedContext.orders.dto.OrderDto;
import com.example.onlineshopping.boundedContext.orders.dto.OrderListDto;
import com.example.onlineshopping.boundedContext.orders.entity.Orders;
import com.example.onlineshopping.boundedContext.orders.service.OrderService;
import com.example.onlineshopping.boundedContext.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final Rq rq;
    private final OrderItemService orderItemService;
    private final ProductService productService;

    @GetMapping("/order")
    public String order(Model model) {
        List<Orders> orders = orderService.getAllOrdersOrderByCreatedAtDesc(rq.getLoginedMemberId());

        Map<Long, List<OrderListDto>> items = new HashMap<Long, List<OrderListDto>>();
        for(Orders order : orders) {
            List<OrderListDto> orderList = new ArrayList<OrderListDto>();
            List<OrderItem> orderItems = orderItemService.getOrderItems(order.getId());
            for(OrderItem orderItem : orderItems) {
                String productName = productService.getProductNameById(orderItem.getProductId());
                if(productName != null) {
                    orderList.add(new OrderListDto(
                            productName,
                            orderItem.getPrice(),
                            orderItem.getQuantity())
                    );
                }
            }
            items.put(order.getId(), orderList);
        }

        model.addAttribute("orders", orders);
        model.addAttribute("items", items);

        return "/usr/orders/order";
    }

    @PostMapping("/order/newOrder")
    public String newOrder(@RequestBody OrderDto orderDto) {
        RsData rsData = orderService.tryAddOrder(rq.getLoginedMemberId(), orderDto);

        return "redirect:/order";
    }

    @PutMapping("/order/cancel/{id}")
    public String cancelOrder(@PathVariable String id) {
        orderService.tryCancelOrder(Long.parseLong(id));

        return "redirect:/order";
    }
}
