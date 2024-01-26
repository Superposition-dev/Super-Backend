package com.superposition.user.service;

import com.superposition.user.dto.UserInfo;
import com.superposition.user.exception.InvalidTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KaKaoLoginServiceTest {
    private final OAuthLoginService oAuthLoginService;

    @Autowired
    public KaKaoLoginServiceTest(OAuthLoginService oAuthLoginService) {
        this.oAuthLoginService = oAuthLoginService;
    }

    @Test
    @DisplayName("잘못된 토큰")
    void 카카오_테스트_실패() {
        //given
        String accessToken = "";

        //when
        InvalidTokenException ie = assertThrows(InvalidTokenException.class,
                () -> oAuthLoginService.getUserInfoByToken(accessToken));

        //then
        assertThat(ie.getClass()).isEqualTo(InvalidTokenException.class);
    }
}