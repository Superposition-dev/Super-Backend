package com.superposition.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ResponseProductDetail {
    private long productId;
    private String picture;
    private String title;
    private String[] tags;
    private String artistName;
    private String instagramId;
    private PictureInfo pictureInfo;
    private String description;
    private int price;
    private String message;
}
