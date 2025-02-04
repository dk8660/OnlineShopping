package com.example.onlineshopping.base.rsData;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData {
    private final String resultCode;
    private final String resultMessage;
    private Object data;

    public static RsData of(String resultCode, String resultMessage) {return of(resultCode, resultMessage, null);}

    public static RsData of(String resultCode, String resultMessage, Object data) {
        return new RsData(resultCode, resultMessage, data);
    }

    public boolean isSuccess() {return resultCode.startsWith("S-");}
}
