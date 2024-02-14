package com.superposition.follow.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.superposition.artist.dto.ArtistInfo;
import com.superposition.follow.domain.mapper.ArtistFollowMapper;
import com.superposition.follow.dto.ArtistFollowDto;
import com.superposition.user.domain.entity.User;
import com.superposition.user.domain.mapper.UserMapper;
import com.superposition.utils.Gender;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("test")
class ArtistFollowServiceImplTest {

    private final ArtistFollowService artistFollowService;
    private final UserMapper userMapper;
    private final ArtistFollowMapper artistFollowMapper;

    @Autowired
    public ArtistFollowServiceImplTest(ArtistFollowService artistFollowService, UserMapper userMapper,
                                       ArtistFollowMapper artistFollowMapper) {
        this.artistFollowService = artistFollowService;
        this.userMapper = userMapper;
        this.artistFollowMapper = artistFollowMapper;
    }

    @Sql({"classpath:user.sql", "classpath:artistFollow.sql"})
    @BeforeEach
    void setup(@Autowired DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("user.sql"));
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("artistFollow.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("팔로우한 작가가 없을 때 작가 팔로우 테스트")
    void test1() {
        //given
        User user = saveUser();

        String email = user.getEmail();
        String instagramId = "320_artwork";
        ArtistFollowDto dto = ArtistFollowDto.builder()
                .email(email)
                .instagramId(instagramId)
                .build();

        //when
        artistFollowService.followArtist(dto);

        //then
        int actual = artistFollowMapper.getFollowCount(email);
        assertThat(actual).isEqualTo(1);
    }

    @Test
    @DisplayName("팔로우한 작가들이 있을 때 작가 팔로우 테스트")
    void test2() {
        //given
        User user = saveUser();

        String email = user.getEmail();
        saveArtistFollow(email, "320_artwork");
        saveArtistFollow(email, "fantasy_filter_studio");
        ArtistFollowDto dto = ArtistFollowDto.builder()
                .email(email)
                .instagramId("i.ru__ol")
                .build();

        //when
        artistFollowService.followArtist(dto);

        //then
        int actual = artistFollowMapper.getFollowCount(email);
        assertThat(actual).isEqualTo(3);
    }

    @Test
    @DisplayName("이미 팔로우한 작가 팔로우 테스트")
    void test3() {
        //given
        User user = saveUser();

        ArtistFollowDto dto = ArtistFollowDto.builder()
                .email(user.getEmail())
                .instagramId("320_artwork")
                .build();
        artistFollowService.followArtist(dto);

        //expected
        assertThatThrownBy(() -> artistFollowService.followArtist(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 팔로우한 작가입니다.");
    }

    @Test
    @DisplayName("팔로우한 작가들 조회 테스트")
    void test4() {
        //given
        User user = saveUser();

        String email = user.getEmail();
        saveArtistFollow(email, "320_artwork");
        saveArtistFollow(email, "fantasy_filter_studio");
        saveArtistFollow(email, "i.ru__ol");

        //when
        List<ArtistInfo> actual = artistFollowService.getFollowArtistsBy(user.getEmail());

        //then
        assertThat(actual)
                .hasSize(3)
                .extracting("instagramId")
                .containsExactlyInAnyOrder("320_artwork", "fantasy_filter_studio", "i.ru__ol");
    }

    @Test
    @DisplayName("팔로우한 작가가 없는 경우 팔로우한 작가들 조회 테스트")
    void test5() {
        //given
        User user = saveUser();

        //when
        List<ArtistInfo> actual = artistFollowService.getFollowArtistsBy(user.getEmail());

        //then
        assertThat(actual.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("팔로우 취소 테스트")
    void test6() {
        //given
        User user = saveUser();

        String email = user.getEmail();
        saveArtistFollow(email, "320_artwork");
        saveArtistFollow(email, "fantasy_filter_studio");
        saveArtistFollow(email, "i.ru__ol");
        ArtistFollowDto dto = ArtistFollowDto.builder()
                .email(email)
                .instagramId("i.ru__ol")
                .build();

        //when
        artistFollowService.deleteArtistFollow(dto);

        //then
        List<ArtistInfo> actual = artistFollowService.getFollowArtistsBy(email);
        assertThat(actual)
                .hasSize(2)
                .extracting("instagramId")
                .containsExactlyInAnyOrder("320_artwork", "fantasy_filter_studio");
    }

    @Test
    @DisplayName("존재하지 않는 작가 팔로우 취소 테스트")
    void test7() {
        // given
        User user = saveUser();

        String email = user.getEmail();
        saveArtistFollow(email, "320_artwork");
        saveArtistFollow(email, "fantasy_filter_studio");
        saveArtistFollow(email, "i.ru__ol");
        ArtistFollowDto dto = ArtistFollowDto.builder()
                .email(email)
                .instagramId("invalid value")
                .build();

        // then
        assertThatThrownBy(() -> artistFollowService.deleteArtistFollow(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 작가입니다.");
    }

    @Test
    @DisplayName("팔로우하지 않은 작가 팔로우 취소 테스트")
    void test8() {
        // given
        User user = saveUser();

        String email = user.getEmail();
        saveArtistFollow(email, "320_artwork");
        saveArtistFollow(email, "fantasy_filter_studio");
        saveArtistFollow(email, "i.ru__ol");
        ArtistFollowDto dto = ArtistFollowDto.builder()
                .email(email)
                .instagramId("leepearl_art")
                .build();

        // then
        assertThatThrownBy(() -> artistFollowService.deleteArtistFollow(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("팔로우하지 않은 작가입니다.");
    }

    private User saveUser() {
        User user = User.builder()
                .email("test@gamil.com")
                .name("testName")
                .nickname("test")
                .profile("profile")
                .gender(Gender.F)
                .birthDate(new Date(2024, 1, 1))
                .build();
        userMapper.saveUserInfo(user);
        return user;
    }

    private void saveArtistFollow(String email, String instagramId) {
        ArtistFollowDto dto = ArtistFollowDto.builder()
                .email(email)
                .instagramId(instagramId)
                .build();
        artistFollowMapper.save(dto);
    }
}