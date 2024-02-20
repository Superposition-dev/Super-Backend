package com.superposition.follow.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArtistFollowDto {
    private final String email;
    private final String instagramId;

    @Builder
    public ArtistFollowDto(String email, String instagramId) {
        this.email = email;
        this.instagramId = instagramId;
    }
}
