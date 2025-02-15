package com.example.onlineshopping.boundedContext.orders.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
    private String productId;
    private int quantity;
    private String cartId;
}
