package com.superposition.user.dto;

import com.superposition.utils.Gender;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ResponseUserInfo {
    private String email;
    private String nickname;
    private String profile;
    private int birthYear;
    private Gender gender;
    private boolean isArtist;
}
