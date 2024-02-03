package com.superposition.user.dto;

import com.superposition.utils.Gender;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

@Getter
@Builder
public class RequestUserInfo {
    @NotBlank(message = "이메일은 필수 데이터입니다.")
    private String email;

    @NotBlank(message = "이름은 필수 데이터입니다.")
    private String name;

    @NotBlank(message = "닉네임은 필수 데이터입니다.")
    @Length(min = 1, max = 100, message = "닉네임은 1~11자만 가능합니다.")
    private String nickname;

    private String profile;
    private Gender gender;
    private Date birthDate;
}
