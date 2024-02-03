package com.superposition.user.service;

import com.superposition.user.dto.JwtToken;
import com.superposition.user.dto.RefreshToken;

public interface TokenService {
    public void setToeknValue(RefreshToken refreshToken);

    public <T> T getTokenValue(String key, Class<T> classType);

    public void deleteTokenValue(String key);
}
