package com.superposition.artist.dto;

import com.superposition.product.dto.SimpleProduct;
import lombok.*;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseArtistDetail {
    private String profile;
    private String name;
    private String introduce;
    private boolean isDisplay;
    private String description;
    private List<SimpleProduct> products;
    private String instagramId;
}
