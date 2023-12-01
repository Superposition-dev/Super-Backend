package com.superposition.product.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductDto {
    private long productId;
    private String picture;
    private String title;
    private String artistName;
    private String description;
    private int price;
}
