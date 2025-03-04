package com.example.onlineshopping.boundedContext.payment.repository;

import com.example.onlineshopping.boundedContext.payment.enity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findById(long id);
}
