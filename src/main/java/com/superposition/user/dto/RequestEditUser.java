package com.superposition.user.dto;

import com.superposition.utils.Gender;
import lombok.Getter;

@Getter
public class RequestEditUser {
    private String email;
    private String nickname;
    private String birthYear;
    private Gender gender;
}
