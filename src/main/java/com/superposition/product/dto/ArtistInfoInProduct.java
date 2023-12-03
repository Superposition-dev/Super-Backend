package com.superposition.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArtistInfoInProduct {
    private String profile;
    private String artistName;
    private String instagramId;
}
