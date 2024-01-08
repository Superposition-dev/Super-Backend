package com.superposition.artist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseArtist extends ResponseDisplayArtist {
    private String profile;
    private String name;
    private String introduce;
    private String instagramId;
    private boolean isDisplay;
}
