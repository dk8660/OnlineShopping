package com.example.onlineshopping.base.initData;

import com.example.onlineshopping.boundedContext.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile({"dev", "test"})
public class NotProd {
    @Bean
    CommandLineRunner initData(MemberService memberService)  {
        return args -> {
            memberService.tryJoin("user1@naver.com", "1234", "user1", "010-0000-0000");
            memberService.tryJoin("Jack1@naver.com", "111", "Jack1", "010-0000-1111");
        };
    }
}
