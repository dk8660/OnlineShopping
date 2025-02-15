package com.example.onlineshopping.boundedContext.orders.service;

import com.example.onlineshopping.base.enums.OrderStatus;
import com.example.onlineshopping.base.rsData.RsData;
import com.example.onlineshopping.boundedContext.cart.service.CartService;
import com.example.onlineshopping.boundedContext.orderItem.entity.OrderItem;
import com.example.onlineshopping.boundedContext.orderItem.entity.service.OrderItemService;
import com.example.onlineshopping.boundedContext.orders.dto.ItemDto;
import com.example.onlineshopping.boundedContext.orders.dto.OrderDto;
import com.example.onlineshopping.boundedContext.orders.entity.Orders;
import com.example.onlineshopping.boundedContext.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final CartService cartService;

    public RsData tryAddOrder(long userId, OrderDto orderDto) {
        try {
            // 결제 시스템 추가 필요

            Orders order = Orders.builder()
                    .userId(userId)
                    .total_price(Integer.parseInt(orderDto.getTotalPrice()))
                    .status(OrderStatus.PENDING)
                    .build();
            orderRepository.save(order);

            // 각 아이템들을 OrderItems에 추가
            List<ItemDto> items = orderDto.getItems();
            for(ItemDto itemDto : items) {
                RsData rsData = orderItemService.tryAddOrderItem(order.getId(), itemDto);
                RsData rsData1 = cartService.tryDelete(Long.parseLong(itemDto.getCartId()));
//                System.out.println("CART ID:" + Long.parseLong(itemDto.getCartId()));
//                System.out.println(rsData1.getResultMessage());
            }

            return RsData.of("S-1", "주문에 성공하였습니다.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return RsData.of("F-1", "주문에 실패하였습니다.");
        }
    }

    public List<Orders> getAllOrders(long id) {
        return orderRepository.findAllByUserId(id);
    }

    public void tryCancelOrder(long id) {

    }
}
