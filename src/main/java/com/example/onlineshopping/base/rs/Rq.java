package com.example.onlineshopping.base.rs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@AllArgsConstructor
public class Rq {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public void setSession(String name, long value) {
        HttpSession session = request.getSession(true);
        session.setAttribute(name, String.valueOf(value));
    }

    public long getSessionAsLong(String name, long defaultValue) {
        String value = getSessionAsString(name, null);
        if(value == null) return defaultValue;
        try {
            return Long.parseLong(value);
        }
        catch(NumberFormatException e) {
            return defaultValue;
        }
    }

    public String getSessionAsString(String name, String defaultValue) {
        try {
            String value = (String) request.getSession(false).getAttribute(name);

            if(value == null) return defaultValue;
            return value;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public boolean removeSession(String name) {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute(name) == null) return false;

        session.removeAttribute(name);
        return true;
    }

    public boolean isLoggedIn() {
        long loginedMember = getSessionAsLong("loginedMemberId", 0);
        return loginedMember != 0;
    }
}
