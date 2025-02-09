package com.example.onlineshopping.boundedContext.cart.controller;

import com.example.onlineshopping.base.rs.Rq;
import com.example.onlineshopping.base.rsData.RsData;
import com.example.onlineshopping.boundedContext.cart.dto.CartItem;
import com.example.onlineshopping.boundedContext.cart.service.CartService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final Rq rq;

    @PostMapping("/cart/add")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addToCart(@RequestBody CartItem cartItem) {
        long memberId = rq.getLoginedMemberId();
        System.out.println(cartItem.getProductId());

        RsData rsData = cartService.tryAddItem(memberId, cartItem.getProductId(), cartItem.getQuantity());

        Map<String, String> response = new HashMap<>();
        response.put("message", rsData.getResultMessage());

        if(rsData.isSuccess()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(response);
    }
}
