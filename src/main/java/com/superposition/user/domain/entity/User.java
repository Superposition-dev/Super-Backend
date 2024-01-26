package com.superposition.user.domain.entity;

import com.superposition.utils.Gender;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Date;

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
    private Timestamp updateAt;
}
