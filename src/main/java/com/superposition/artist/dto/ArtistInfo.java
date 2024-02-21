package com.superposition.artist.dto;

import java.util.Objects;
import lombok.Getter;

@Getter
public class ArtistInfo {
    private String profile;
    private String name;
    private String introduce;
    private boolean isDisplay;
    private String description;
    private String instagramId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArtistInfo that = (ArtistInfo) o;
        return Objects.equals(getInstagramId(), that.getInstagramId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInstagramId());
    }
}
