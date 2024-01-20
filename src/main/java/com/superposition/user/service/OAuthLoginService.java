package com.superposition.user.service;

import com.superposition.user.dto.KakaoResponse;

public interface OAuthLoginService {
    public String getAccessTokenByCode(String code);
    public KakaoResponse getUserInfoByToken(String accessToken);
}
