package com.superposition.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ResponseProduct {
    private long productId;
    private String picture;
    private String[] tags;
    private String title;
    private String artist;
}
