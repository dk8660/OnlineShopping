package com.example.onlineshopping.boundedContext.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private long orderId;

    @Column(nullable = false)
    private long productId;

    private int quantity;

    private int price;

}
