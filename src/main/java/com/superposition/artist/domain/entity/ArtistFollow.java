package com.superposition.artist.domain.entity;

import java.util.Date;
import lombok.Getter;

@Getter
public class ArtistFollow {
    private String email;
    private String instagramId;
    private Date createAt;
}
