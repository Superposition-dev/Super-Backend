package com.superposition.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PictureInfo {
    private String type;
    private String size;
    private int year;
}
