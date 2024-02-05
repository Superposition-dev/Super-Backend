package com.superposition.user.domain.entity;

import com.superposition.utils.Gender;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private String email; //회원 ID, 변경 불가
    private String name;
    private String nickname;
    private String profile;
    private Gender gender;
    private Date birthDate;
    private Timestamp createAt;
    private String following;

    public boolean isFollowed(final String artistInstagramId) {
        return Arrays.asList(following.split(","))
                .contains(artistInstagramId);
    }

    public boolean isEmptyFollowing() {
        return following == null;
    }

    public List<String> getFollowingArtistInstagramIds() {
        return Arrays.stream(following.split(","))
                .toList();
    }
}
