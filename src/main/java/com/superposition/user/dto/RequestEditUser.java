package com.superposition.user.dto;

import com.superposition.utils.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestEditUser {
    private String email;
    private String nickname;
    private int birthYear;
    private Gender gender;
}
