package com.example.onlineshopping.base.enums;

public enum PaymentStatus {
    PENDING("결제 중"),
    SUCCESS("결제 성공"),
    FAILED("결제 실패");

    private final String text;

    PaymentStatus(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
