package com.superposition.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class KakaoResponse {
    private long id;
    private String email;
    private String nickname;
    private String profileImage;
}
