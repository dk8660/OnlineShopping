package com.example.onlineshopping.boundedContext.cart.service;

import com.example.onlineshopping.base.rsData.RsData;
import com.example.onlineshopping.boundedContext.cart.entity.Cart;
import com.example.onlineshopping.boundedContext.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public RsData tryAddItem(long memberId, long productId, int quantity) {
        try {
            Cart cart = Cart.builder()
                    .userId(memberId)
                    .productId(productId)
                    .quantity(quantity)
                    .build();
            cartRepository.save(cart);

            return RsData.of("S-1", "상품을 장바구니에 추가하였습니다.");
        } catch (Exception e) {
            return RsData.of("F-1", "장바구니 추가에 실패하였습니다.");
        }
    }
}
