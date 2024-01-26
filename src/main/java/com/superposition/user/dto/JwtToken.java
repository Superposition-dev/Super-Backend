package com.superposition.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtToken {
    private String email;
    private String grantType; // Bearer
    private String accessToken;
    private long accessTokenExpiresIn;
    private RefreshToken refreshToken;
}
