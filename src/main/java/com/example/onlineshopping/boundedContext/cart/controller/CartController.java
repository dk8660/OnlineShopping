package com.example.onlineshopping.boundedContext.cart.controller;

import com.example.onlineshopping.base.rs.Rq;
import com.example.onlineshopping.base.rsData.RsData;
import com.example.onlineshopping.boundedContext.cart.dto.CartItem;
import com.example.onlineshopping.boundedContext.cart.entity.Cart;
import com.example.onlineshopping.boundedContext.cart.service.CartService;
import com.example.onlineshopping.boundedContext.product.entity.Product;
import com.example.onlineshopping.boundedContext.product.service.ProductService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final Rq rq;
    private final ProductService productService;

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

    @GetMapping("/cart")
    public String showCart(Model model) {
        List<Cart> items = cartService.tryGetItems(rq.getLoginedMemberId());
        List<Product> products = new ArrayList<Product>();
        if(items != null && !items.isEmpty()) {
            for(Cart item : items) {
                Product product = productService.getProductById(item.getProductId());
                if(product != null) products.add(product);
            }
            model.addAttribute("items", items);
            model.addAttribute("products", products);
        }
        
        return "/usr/cart/cart";
    }

    @DeleteMapping("/cart/delete/{id}")
    public String deleteFromCart(@PathVariable long id) {
        RsData rsData = cartService.tryDelete(id);

        return "redirect:/cart";
    }
}
