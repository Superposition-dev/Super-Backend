package com.superposition.artist.service;

import static org.assertj.core.api.Assertions.*;

import com.superposition.artist.dto.ArtistFollowDto;
import com.superposition.artist.dto.ArtistInfo;
import com.superposition.user.domain.entity.User;
import com.superposition.user.domain.mapper.UserMapper;
import com.superposition.utils.Gender;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @Autowired
    public ArtistFollowServiceImplTest(ArtistFollowService artistFollowService, UserMapper userMapper) {
        this.artistFollowService = artistFollowService;
        this.userMapper = userMapper;
    }

    @Sql({"classpath:user.sql"})
    @BeforeEach
    void setup(@Autowired DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("user.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @CsvSource(value = {":320_artwork:320_artwork", "320_artwork,fantasy_filter_studio:i.ru__ol:320_artwork,fantasy_filter_studio,i.ru__ol"}, delimiter = ':')
    @DisplayName("작가 팔로잉 테스트")
    void followArtistTest(String existsFollowing, String artistInstagramId, String expected) {
        //given
        User user = saveUser(existsFollowing);

        ArtistFollowDto dto = ArtistFollowDto.builder()
                .userEmail(user.getEmail())
                .artistInstagramId(artistInstagramId)
                .build();

        //when
        artistFollowService.followArtist(dto);

        //then
        String actual = userMapper.findByEmail(user.getEmail()).get().getFollowing();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("이미 팔로우한 작가 팔로잉 테스트")
    void followArtistFailTest() {
        //given
        User user = saveUser(null);

        ArtistFollowDto dto = ArtistFollowDto.builder()
                .userEmail(user.getEmail())
                .artistInstagramId("320_artwork")
                .build();
        artistFollowService.followArtist(dto);

        //expected
        Assertions.assertThatThrownBy(() -> artistFollowService.followArtist(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 팔로우한 작가입니다.");
    }

    @Test
    @DisplayName("팔로잉한 작가들 조회 테스트")
    void getFollowingArtistsTest() {
        //given
        User user = saveUser("320_artwork,fantasy_filter_studio,i.ru__ol");

        //when
        List<ArtistInfo> actual = artistFollowService.getFollowingArtists(user.getEmail());

        //then
        String following = userMapper.findByEmail(user.getEmail()).get().getFollowing();
        List<String> expected = Arrays.stream(following.split(","))
                .toList();
        assertThat(actual)
                .hasSize(expected.size())
                .extracting("instagramId")
                .containsExactlyInAnyOrder("320_artwork", "fantasy_filter_studio", "i.ru__ol");
    }

    @Test
    @DisplayName("팔로잉한 작가가 없는 경우 팔로잉한 작가들 조회 테스트")
    void getFollowingArtistsEmptyTest() {
        //given
        User user = saveUser(null);

        //when
        List<ArtistInfo> actual = artistFollowService.getFollowingArtists(user.getEmail());

        //then
        assertThat(actual.isEmpty()).isTrue();
    }

    private User saveUser(String following) {
        User user = User.builder()
                .email("test@gamil.com")
                .name("testName")
                .nickname("test")
                .profile("profile")
                .gender(Gender.F)
                .birthDate(new Date(2024, 1, 1))
                .build();
        userMapper.saveUserInfo(user);
        userMapper.updateFollow(user.getEmail(), following);
        return user;
    }
}