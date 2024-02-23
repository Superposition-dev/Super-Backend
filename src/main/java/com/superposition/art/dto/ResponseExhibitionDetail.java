package com.superposition.art.dto;

import com.superposition.artist.dto.ArtistInfo;
import com.superposition.product.dto.ResponseProduct;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ResponseExhibitionDetail {
    private long exhibitionId;
    private String title;
    private String subHeading;
    private List<ResponseProduct> productInfo;
    private Set<ArtistInfo> artistInfo;
}
