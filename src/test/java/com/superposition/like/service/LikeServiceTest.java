package com.superposition.like.service;

import com.superposition.product.exception.NoExistProductException;
import com.superposition.product.service.ProductService;
import com.superposition.user.dto.RequestUserInfo;
import com.superposition.user.dto.UserInfo;
import com.superposition.user.service.UserService;
import com.superposition.utils.Gender;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class LikeServiceTest {
    private final LikeService likeService;
    private final UserService userService;
    private RequestUserInfo user;

    @Autowired
    public LikeServiceTest(LikeService likeService, UserService userService) {
        this.likeService = likeService;
        this.userService = userService;
    }

    @BeforeEach
    void setUp() {
        user = RequestUserInfo.builder()
                .email("test@email.com")
                .name("test")
                .nickname("test")
                .gender(Gender.F)
                .birthDate(new Date(2004, 02, 27))
                .profile("testProfile").build();

        userService.signup(user);
    }

    @AfterAll
    static void afterAll() {

    }

    @Test
    @DisplayName("좋아요 추가 성공 테스트")
    void 좋아요_추가() {
        //given
        long productId = 1L;
        String email = user.getEmail();

        //when
        likeService.likeProduct(productId, email);

        //then
        boolean like = likeService.isLike(productId, email);
        assertThat(like).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 게시물 좋아요 - 실패")
    void 좋아요_추가_2() {
        //given
        long productId = 30L;
        String email = user.getEmail();

        //when
        NoExistProductException ne =
                assertThrows(NoExistProductException.class, () -> likeService.likeProduct(productId, email));

        assertThat(ne.getClass()).isEqualTo(NoExistProductException.class);
    }

    @Test
    @DisplayName("좋아요 취소 성공 테스트")
    void 좋아요_취소() {
        //given
        long productId = 1L;
        String email = user.getEmail();
        likeService.likeProduct(productId, email);

        //when
        likeService.dislikeProduct(productId, email);

        //then
        boolean like = likeService.isLike(productId, email);
        assertThat(like).isFalse();
    }
}