package com.example.onlineshopping.boundedContext.cart.service;

import com.example.onlineshopping.base.rsData.RsData;
import com.example.onlineshopping.boundedContext.cart.entity.Cart;
import com.example.onlineshopping.boundedContext.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Cart> tryGetItems(long id) {
        List<Cart> items = cartRepository.findAllByUserId(id);
        if (!items.isEmpty()) {
            return items;
        }
        return null;
    }

    public RsData tryDelete(long id) {
        try {
//            System.out.println("ID: " + id);
            cartRepository.deleteById(id);
            return RsData.of("S-1", "장바구니에서 삭제하였습니다.");
        }
        catch (Exception e) {
            return RsData.of("F-1", "삭제에 실패하였습니다.");
        }
    }
}
