package com.example.onlineshopping.base.enums;

public enum OrderStatus {
    PENDING("주문 등록"),
    PAID("결제 완료"),
    CANCELLED("주문 취소"),
    DELIVERING("배송 중"),
    DELIVERED("배송 완료");

    private final String text;

    OrderStatus(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
