package com.superposition.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ResponseProduct {
    private String productId;
    private long productNum;
    private String picture;
    private String[] tags;
    private String title;
    private String artist;
}
