package com.example.onlineshopping.base.rs;

import com.example.onlineshopping.base.security.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;

@Component
@RequestScope
@AllArgsConstructor
public class Rq {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final JwtProvider jwtProvider;

    public void setJwtToken(long memberId) {
        String token = jwtProvider.generateToken(memberId);
        setCookie("Authorization", token);
    }

    public long getLoginedMemberId() {
        String token = extractJwtToken();
        if(token == null) return 0L;
        try {
            System.out.println(jwtProvider.getUserIdFromToken(token));
            return jwtProvider.getUserIdFromToken(token);
        } catch (Exception e) {
            return 0L;
        }
    }

    private String extractJwtToken() {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    System.out.println(cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public boolean removeJwtToken() {
        if(getLoginedMemberId() == 0L) return false;

        setCookie("Authorization", null);

        return true;
    }

    public boolean isLogined() {
        return getLoginedMemberId() != 0L;
    }

//    public void setSession(String name, long value) {
//        HttpSession session = request.getSession(true);
//        session.setAttribute(name, String.valueOf(value));
//    }
//
//    public long getSessionAsLong(String name, long defaultValue) {
//        String value = getSessionAsString(name, null);
//        if(value == null) return defaultValue;
//        try {
//            return Long.parseLong(value);
//        }
//        catch(NumberFormatException e) {
//            return defaultValue;
//        }
//    }
//
//    public String getSessionAsString(String name, String defaultValue) {
//        try {
//            String value = (String) request.getSession(false).getAttribute(name);
//
//            if(value == null) return defaultValue;
//            return value;
//        } catch (Exception e) {
//            return defaultValue;
//        }
//    }
//
//    public boolean removeSession(String name) {
//        HttpSession session = request.getSession(false);
//        if(session == null || session.getAttribute(name) == null) return false;
//
//        session.removeAttribute(name);
//        return true;
//    }
//
//    public boolean isLoggedIn() {
//        long loginedMember = getSessionAsLong("loginedMemberId", 0);
//        return loginedMember != 0;
//    }

    public void setCookie(String name, long value) {
        setCookie(name, value + "");
    }

    public void setCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public boolean removeCookie(String name) {
        Cookie cookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals(name))
                .findFirst().orElse(null);
        if(cookie != null) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return true;
        }
        return false;
    }

    public long getCookieAsLong(String name, long defaultValue) {
        String value = getCookie(name, null);

        if(value == null) return defaultValue;
        try {
            return Long.parseLong(value);
        }
        catch(NumberFormatException e) {
            return defaultValue;
        }
    }

    public String getCookie(String name, String defaultValue) {
        if(request.getCookies() == null) return defaultValue;
        return Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(name))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(defaultValue);
    }
}
