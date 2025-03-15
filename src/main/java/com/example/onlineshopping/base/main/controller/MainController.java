package com.example.onlineshopping.base.main.controller;

import com.example.onlineshopping.base.rs.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final Rq rq;

    @GetMapping("/")
    public String showMainPage(Model model) {
        if(rq == null) return "payment/404";

        model.addAttribute(rq);

        return "main/index";
    }
}
