package com.example.onlineshopping.boundedContext.member.controller;

import com.example.onlineshopping.base.rs.Rq;
import com.example.onlineshopping.base.rsData.RsData;
import com.example.onlineshopping.boundedContext.member.entity.Member;
import com.example.onlineshopping.boundedContext.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@AllArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/member/login")
    public String showLoginPage() {
        if(rq.isLogined()) return "/usr/member/alreadyLogined";
        return "/usr/member/login";
    }

    @PostMapping("/member/doLogin")
    @ResponseBody
    public RsData login(String email, String password) {
        RsData rsData = memberService.tryLogin(email, password);
        if(rsData.isSuccess()) {
            Member member = (Member) rsData.getData();
//            rq.setSession("loginedMemberId", member.getId());
            rq.setJwtToken(member.getId());
        }
        return rsData;
    }

    @GetMapping("member/join")
    public String showJoinPage() {
        if(rq.isLogined()) return "/usr/member/alreadyLogined";
        return "/usr/member/join";
    }

    @PostMapping("member/doJoin")
    @ResponseBody
    public RsData join(String email, String password, String name, String phone) {
        RsData rsData = memberService.tryJoin(email, password, name, phone);
        return rsData;
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public RsData logout() {
//        boolean removed = rq.removeSession("loginedMemberId");
        boolean removed = rq.removeJwtToken();
        if(!removed) {
            return RsData.of("F-1", "이미 로그아웃 상태입니다.");
        }
        return RsData.of("S-1", "로그아웃 되었습니다.");
    }
}
