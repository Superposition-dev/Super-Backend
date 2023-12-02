package com.superposition.artist.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@ToString
public class ResponseArtistDetail {
    private String profile;
    private String name;
    private String introduce;
    private boolean isDisplay;
    private String description;
    private List<Map<Integer, String>> products;
    private String instaId;
}
