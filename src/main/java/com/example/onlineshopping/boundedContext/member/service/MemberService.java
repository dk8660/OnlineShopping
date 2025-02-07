package com.example.onlineshopping.boundedContext.member.service;

import com.example.onlineshopping.base.enums.Role;
import com.example.onlineshopping.base.rsData.RsData;
import com.example.onlineshopping.boundedContext.member.entity.Member;
import com.example.onlineshopping.boundedContext.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public RsData tryLogin(String email, String password) {
        if(email == null || email.trim().isEmpty()) return RsData.of("F-1", "이메일을 입력해주세요.");
        if(password == null || password.trim().isEmpty()) return RsData.of("F-2", "비밀번호를 입력해주세요.");

        Member member = memberRepository.findByEmail(email).orElse(null);

        if(member == null) return RsData.of("F-3", "존재하지 않는 계정입니다.");
        if(!member.getPassword().equals(password)) return RsData.of("F-4", "암호가 일치하지 않습니다.");

        return RsData.of("S-1", "%s님 환영합니다.".formatted(member.getName()), member);
    }

    public RsData tryJoin(String email, String password, String name, String phone, String role) {
        if(email == null || email.trim().isEmpty()) return RsData.of("F-1", "이메일을 입력해주세요.");
        if(password == null || password.trim().isEmpty()) return RsData.of("F-2", "비밀번호를 입력해주세요.");
        if(name == null || name.trim().isEmpty()) return RsData.of("F-3", "이름을 입력해주세요.");
        if(phone == null || phone.trim().isEmpty()) return RsData.of("F-4", "전화번호를 입력해주세요.");

        Member member = memberRepository.findByEmail(email).orElse(null);
        if(member != null) return RsData.of("F-5", "이미 가입된 이메일입니다.");

        Role r;
        if(role.equals("판매자")) r = Role.SELLER;
        else r = Role.USER;


        member = Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .role(r)
                .build();
        memberRepository.save(member);

        return RsData.of("S-1", "회원가입에 성공하였습니다.");
    }
}
