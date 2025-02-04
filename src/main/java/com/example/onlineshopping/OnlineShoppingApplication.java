package com.example.onlineshopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OnlineShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShoppingApplication.class, args);
    }

}
