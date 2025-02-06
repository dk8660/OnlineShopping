package com.example.onlineshopping.base.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    private final String SECRET_KEY = "ThisIsA256BitSecretKeyForJwtThatIsLongEnough123";
    private final long EXPIRATION_TIME = 1000 * 60 * 30; // 30분

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    //JWT 생성
    public String generateToken(long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // userId를 token의 subject(주체)로 저장
                .setIssuedAt(new Date()) // 토큰의 생성된 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 유효 범위
                .signWith(key, SignatureAlgorithm.HS256) // HMAC-SHA256 알고리즘으로 서명
                .compact(); // 최종적으로 JWT 문자열 생성 후 반환
    }

    public long getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key) // SECRET_KEY를 이용해 서명 검증
                    .build()
                    .parseClaimsJws(token)
                    .getBody(); // 주어진 token을 검증하고 안에 들어 있는 Claims(토큰 정보) 가져오기

            return Long.parseLong(claims.getSubject()); // 저장된 사용자 ID를 꺼내기
        } catch (Exception e) {
            System.out.println(e);
            return 0L;
        }
    }
}
