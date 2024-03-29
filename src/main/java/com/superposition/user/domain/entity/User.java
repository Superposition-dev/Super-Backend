package com.superposition.user.domain.entity;

import com.superposition.utils.Gender;
import java.sql.Timestamp;
import java.util.Date;
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
    private int birthYear;
    private Timestamp createAt;
}
