package com.superposition.artist.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArtistFollowDto {
    private final String userEmail;
    private final String artistInstagramId;

    @Builder

    public ArtistFollowDto(String userEmail, String artistInstagramId) {
        this.userEmail = userEmail;
        this.artistInstagramId = artistInstagramId;
    }
}
