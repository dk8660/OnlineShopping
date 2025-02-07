package com.example.onlineshopping.boundedContext.product.controller;

import com.example.onlineshopping.base.enums.Role;
import com.example.onlineshopping.base.rs.Rq;
import com.example.onlineshopping.base.rsData.RsData;
import com.example.onlineshopping.boundedContext.member.entity.Member;
import com.example.onlineshopping.boundedContext.member.repository.MemberRepository;
import com.example.onlineshopping.boundedContext.product.entity.Product;
import com.example.onlineshopping.boundedContext.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final MemberRepository memberRepository;
    private final ProductService productService;
    private final Rq rq;

    @GetMapping("/product")
    public String showAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products); // model을 사용하여 HTML로 데이터를 전달
        return "/product/products";
    }

    @GetMapping("/product/myproducts")
    public String showMyProducts(Model model) {
        long sellerId = rq.getLoginedMemberId();

        List<Product> products = productService.getAllProducts();
        List<Product> myProducts = new ArrayList<>();

        for (Product product : products) {
            if(product.getSeller() == sellerId){myProducts.add(product);}
        }

        model.addAttribute("products", myProducts);
        return "/product/myproducts";
    }

    @GetMapping("/product/register")
    public String showProductRegister() {
        Member member = memberRepository.findById(rq.getLoginedMemberId()).orElse(null);
        if(member == null || member.getRole().equals(Role.USER)) {return "redirect:/product";}
        return "/product/register";
    }

    @PostMapping("/product/doProductRegister")
    @ResponseBody
    public RsData doProductRegister(String name, String description, String price, String stock, String category) {
        RsData rsdata = productService.tryRegister(name, description, price, stock, category, rq.getLoginedMemberId());
        return rsdata;
    }

    @DeleteMapping("/product/delete/{id}")
    public String doProductDelete(@PathVariable String id) {
        RsData rsdata = productService.tryDelete(Long.parseLong(id));

        return "redirect:/product/myproducts";
    }

    @GetMapping("/product/update/{id}")
    public String showProductRegister(@PathVariable String id, Model model) {
        Member member = memberRepository.findById(rq.getLoginedMemberId()).orElse(null);
        if(member == null || !member.getRole().equals(Role.SELLER)) {return "/product/products";}

        Product product = productService.getProductById(Long.parseLong(id));
        if(product == null) {return "/pageNotFound";}
        model.addAttribute("product", product);

        return "/product/update";
    }

    @PutMapping("/product/doUpdateProduct")
    public String doProductUpdate(String id, String name, String description, String price, String stock, String category) {
        RsData rsdata = productService.tryUpdate(Long.parseLong(id), name, description, price, stock, category);
        System.out.println(rsdata);
        return "redirect:/product/myproducts";
    }
}
