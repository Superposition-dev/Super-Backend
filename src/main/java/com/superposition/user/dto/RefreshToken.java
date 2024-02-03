package com.superposition.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshToken {
    private String email;
    private String refreshToken;
}
