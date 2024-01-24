package com.superposition.user.dto;

import com.superposition.utils.Gender;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class RequestUserInfo {
    private String email;
    private String nickname;
    private String profile;
    private Gender gender;
    private Date birthDate;

    private RequestUserInfo(){}
}
