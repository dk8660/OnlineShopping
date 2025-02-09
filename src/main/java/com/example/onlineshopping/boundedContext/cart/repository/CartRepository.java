package com.example.onlineshopping.boundedContext.cart.repository;

import com.example.onlineshopping.boundedContext.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findById(long id);
}
