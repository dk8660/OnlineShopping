package com.example.onlineshopping.base.enums;

public enum Role {
    ADMIN("관리자"),
    USER("일반 회원"),
    SELLER("판매자");

    private final String text;

    Role(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
