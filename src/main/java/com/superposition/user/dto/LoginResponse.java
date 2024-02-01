package com.superposition.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private ResponseUserInfo userInfo;
    private String accessToken;
}
