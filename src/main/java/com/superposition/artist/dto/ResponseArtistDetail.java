package com.superposition.artist.dto;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public class ResponseArtistDetail {
    private String profile;
    private String name;
    private String introduce;
    private boolean isDisplay;
    private String description;
    private List<Map<String, String>> products;
    private String instaId;
}