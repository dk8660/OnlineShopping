package com.example.onlineshopping.base.initData;

import com.example.onlineshopping.boundedContext.member.service.MemberService;
import com.example.onlineshopping.boundedContext.product.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile({"dev", "test"})
public class NotProd {
    @Bean
    CommandLineRunner initData(MemberService memberService, ProductService productService)  {
        return args -> {
            memberService.tryJoin("user1@naver.com", "1234", "user1", "010-0000-0000", "일반 회원");
            memberService.tryJoin("Jack1@naver.com", "111", "Jack1", "010-0000-1111", "판매자");

            productService.tryRegister("상품1", "설명1", "30000", "1000", "잡화", 2L);
            productService.tryRegister("상품2", "설명2", "10000", "200", "의류", 2L);
            productService.tryRegister("상품3", "설명3", "50000", "100", "식품", 2L);
        };
    }
}
