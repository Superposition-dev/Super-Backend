package com.superposition.product.domain.entity;

import lombok.Getter;

@Getter
public class Product {
    private String productId; //UUID
    private long productNum;
    private String picture;
    private String title;
    private String artistName;
    private String description;
    private int price;
}
