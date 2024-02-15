package com.superposition.user.service;

import com.superposition.like.service.LikeService;
import com.superposition.product.dto.ProductListDto;
import com.superposition.product.dto.ResponseProduct;
import com.superposition.user.dto.RequestEditUser;
import com.superposition.user.dto.RequestUserInfo;
import com.superposition.user.exception.ForbiddenException;
import com.superposition.utils.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MpageServiceImplTest {
    private final MpageService mpageService;
    private final UserService userService;
    private final LikeService likeService;

    private RequestUserInfo userInfo;

    @Autowired
    MpageServiceImplTest(MpageService mpageService, UserService userService, LikeService likeService) {
        this.mpageService = mpageService;
        this.userService = userService;
        this.likeService = likeService;
    }

    @BeforeEach
    void setUp() {
        userInfo = RequestUserInfo.builder().email("test@gamil.com").name("testName").nickname("test").profile("profile").gender(Gender.F).birthYear(2024).build();
        userService.signup(userInfo);
    }

    @Test
    void 마이페이지_정보_조회() {
        //given
        String email = userInfo.getEmail();

        //when
        ResponseEntity<?> response = mpageService.getUserInfo(email);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("마이페이지 정보 조회 시 이메일이 없는 경우")
    void 마이페이지_정보_조회_실패() {
        //given
        String email = null;

        //when
        ForbiddenException fe
                = assertThrows(ForbiddenException.class, () -> mpageService.getUserInfo(email));

        //then
        assertThat(fe.getClass()).isEqualTo(ForbiddenException.class);
    }

    @Test
    void 좋아요_조회_테스트() {
        //given
        String email = userInfo.getEmail();
        likeService.likeProduct(1, email);

        //when
        List<ResponseProduct> userLikeProducts = mpageService.getUserLikeProducts(email);

        //then
        assertThat(userLikeProducts.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("좋아요 목록 조회 시 이메일이 없는 경우")
    void 좋아요_조회_실패() {
        //given
        String email = null;

        //when
        ForbiddenException fe
                = assertThrows(ForbiddenException.class, () -> mpageService.getUserLikeProducts(email));

        //then
        assertThat(fe.getClass()).isEqualTo(ForbiddenException.class);
    }

    @Test
    @DisplayName("현재 유저 이메일 정보와 전달받은 유저 정보가 다를 경우")
    void 정보_수정_테스트() {
        String email = userInfo.getEmail();
        RequestEditUser userEditInfo = RequestEditUser.builder()
                .email("test2")
                .nickname("test2")
                .birthYear(2024)
                .gender(Gender.F).build();

        //when
        ForbiddenException fe
                = assertThrows(ForbiddenException.class, () -> mpageService.editUserInfo(email, userEditInfo));

        //then
        assertThat(fe.getClass()).isEqualTo(ForbiddenException.class);
    }
}