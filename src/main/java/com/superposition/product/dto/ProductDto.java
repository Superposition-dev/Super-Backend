package com.superposition.product.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductDto {
    private String productId;
    private long productNum;
    private String picture;
    private String title;
    private String artistName;
    private String description;
    private int price;
}
