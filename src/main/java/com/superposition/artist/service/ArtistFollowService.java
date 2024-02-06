package com.superposition.artist.service;

import com.superposition.artist.dto.ArtistFollowDto;
import com.superposition.artist.dto.ArtistInfo;
import java.util.List;

public interface ArtistFollowService {
    void followArtist(ArtistFollowDto dto);

    List<ArtistInfo> getFollowArtistsBy(String email);
}
