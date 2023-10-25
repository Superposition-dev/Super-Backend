package com.superposition.product.domain.entity;

import lombok.Getter;

@Getter
public class Product {
    private long productId;
    private String title;
    private String picture;
    private int likeCount;
    private int viewCount;
    private String artistName;
}
