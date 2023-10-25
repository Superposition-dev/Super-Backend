package com.superposition.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ResponseProduct {
    private long productId;
    private String title;
    private String picture;
    private int likeCount;
    private int viewCount;
    private String artistName;
    private String profileImg;
    private List<TagList> tag;
}
