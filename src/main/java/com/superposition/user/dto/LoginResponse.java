package com.superposition.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class LoginResponse {
    private KakaoResponse kakaoResponse;
    private TokenDTO token;

    private LoginResponse(){}

    public LoginResponse(KakaoResponse kakaoResponse, TokenDTO token) {
        this.kakaoResponse = kakaoResponse;
        this.token = token;
    }
}
