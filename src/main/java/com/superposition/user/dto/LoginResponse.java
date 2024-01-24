package com.superposition.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private UserInfo userInfo;
    private String accessToken;
}
