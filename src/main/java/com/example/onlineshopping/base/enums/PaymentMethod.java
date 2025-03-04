package com.example.onlineshopping.base.enums;

public enum PaymentMethod {
    CARD("카드"),
    LINE_PAY("라인 페이"),
    BANK_TRANSFER("계좌이체");

    private final String text;

    PaymentMethod(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
