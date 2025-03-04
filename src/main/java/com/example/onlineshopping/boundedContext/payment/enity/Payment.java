package com.example.onlineshopping.boundedContext.payment.enity;

import com.example.onlineshopping.base.enums.PaymentMethod;
import com.example.onlineshopping.base.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private long orderId;

    private Enum<PaymentMethod> paymentMethod;

//    private long transactionId;

    private int amount;

    private Enum<PaymentStatus> status;

    @CreatedDate
    private LocalDateTime paidAt;
}
