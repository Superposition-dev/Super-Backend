package com.superposition.user.service.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.superposition.user.jwt.dto.RefreshToken;
import com.superposition.user.exception.InvalidTokenException;
import com.superposition.user.exception.ParsingException;
import com.superposition.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisTokenService implements TokenService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public void setToeknValue(RefreshToken refreshToken) {
        String key = refreshToken.getEmail();
        try {
            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(refreshToken));
            redisTemplate.expire(key, JwtUtils.REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS); //TTL 7 days
        } catch (JsonProcessingException e) {
            throw new ParsingException();
        }
    }

    public <T> T  getTokenValue(String key, Class<T> classType){
        String redisValue = (String)redisTemplate.opsForValue().get(key);
        if (StringUtils.hasText(redisValue)){
            try {
                return objectMapper.readValue(redisValue, classType);
            } catch (JsonProcessingException e) {
                throw new ParsingException();
            }
        } else {
            throw new InvalidTokenException();
        }
    }

    @Override
    public void deleteTokenValue(String key) {
        redisTemplate.delete(key);
    }
}
