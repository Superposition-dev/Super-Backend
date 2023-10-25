package com.superposition.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ResponseProducts {
    private long postId;
    private String profileImg;
    private String name;
    private String picture;
    private String title;
    private int likes;
    private int views;
    private List<TagList> tag;
}
