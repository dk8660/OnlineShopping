package com.example.onlineshopping.boundedContext.orders.repository;

import com.example.onlineshopping.boundedContext.orders.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findById(long id);
    List<Orders> findAllByUserId(long id);
    List<Orders> findAllByUserIdOrderByCreatedAtDesc(long id);
}
