package com.superposition.user.service.token;

import com.superposition.user.jwt.dto.RefreshToken;

public interface TokenService {
    public void setToeknValue(RefreshToken refreshToken);

    public <T> T getTokenValue(String key, Class<T> classType);

    public void deleteTokenValue(String key);
}
