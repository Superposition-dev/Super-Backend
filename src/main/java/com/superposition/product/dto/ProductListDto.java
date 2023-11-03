package com.superposition.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductListDto {
    private long productId;
    private String picture;
    private String title;
    private String artistName;
}
