package com.superposition.artist.dto;

import lombok.Builder;

@Builder
public class ResponseArtist extends ResponseDisplayArtist{
    private String profile;
    private String name;
    private String introduce;
    private boolean isDisplay;
}
