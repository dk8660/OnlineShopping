package com.example.onlineshopping.boundedContext.orderItem.entity.service;

import com.example.onlineshopping.base.rsData.RsData;
import com.example.onlineshopping.boundedContext.orderItem.entity.OrderItem;
import com.example.onlineshopping.boundedContext.orderItem.entity.repository.OrderItemRepository;
import com.example.onlineshopping.boundedContext.orders.dto.ItemDto;
import com.example.onlineshopping.boundedContext.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;

    public RsData tryAddOrderItem(long orderId, ItemDto itemDto) {
        try {
            long productId = Long.parseLong(itemDto.getProductId());
            OrderItem orderItem = OrderItem.builder()
                    .orderId(orderId)
                    .productId(productId)
                    .quantity(itemDto.getQuantity())
                    .price(productService.tryGetPrice(productId))
                    .build();
            orderItemRepository.save(orderItem);

            return RsData.of("S-1", "세부 상품 주문 등록에 성공하였습니다.");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return RsData.of("F-1", "세부 상품 주문 등록에 실패하였습니다.");
        }
    }

    public List<OrderItem> getOrderItems(long orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }
}
