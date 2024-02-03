package com.superposition.user.service;

import com.superposition.user.dto.UserInfo;

public interface OAuthLoginService {
    public String getAccessTokenByCode(String code);
    public UserInfo getUserInfoByToken(String accessToken);
}
