package com.example.onlineshopping.boundedContext.orders.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    private String totalPrice;
    private List<ItemDto> items;
}
