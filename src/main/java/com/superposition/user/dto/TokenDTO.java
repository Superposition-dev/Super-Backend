package com.superposition.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDTO {
    private String grantType;   // Bearer
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
}
