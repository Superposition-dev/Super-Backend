package com.superposition.art.domain.entity;

import lombok.Getter;

@Getter
public enum ExhibitionStatus {
    CURRENT("전시중"),
    END("전시 종료");

    private final String value;

    ExhibitionStatus(String value) {
        this.value = value;
    }
}
