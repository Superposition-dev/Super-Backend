package com.superposition.art.dto;

import com.superposition.artist.dto.ArtistInfo;
import com.superposition.product.dto.ResponseProduct;
import java.util.List;
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
    private List<ArtistInfo> artistInfo;
}
