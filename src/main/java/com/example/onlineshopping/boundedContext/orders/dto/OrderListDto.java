package com.example.onlineshopping.boundedContext.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderListDto {
    private String productName;
    private int price;
    private int quantity;
}
