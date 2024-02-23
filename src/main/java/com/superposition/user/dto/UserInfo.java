package com.superposition.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserInfo {
    private String email;
    private String nickname;
    private String profile;
}
