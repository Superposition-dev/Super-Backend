package com.superposition.canex.service;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecipientForRequest {

    private String address = null; // 수신자 이메일
    private String name = null; // 수신자 이름
    private String type = "R"; // R: 수신자, C: 참조인, B: 숨은참조
    private Object parameters = null; // 치환 파라미터

    protected RecipientForRequest(String address, String name) {
        this.address = address;
        this.name = name;
    }

    public static RecipientForRequest of(String address, String name) {
        return new RecipientForRequest(address, name);
    }
}