package com.example.onlineshopping.boundedContext.orderItem.entity.repository;

import com.example.onlineshopping.boundedContext.orderItem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findById(long id);
    List<OrderItem> findAllByOrderId(long orderId);
}
