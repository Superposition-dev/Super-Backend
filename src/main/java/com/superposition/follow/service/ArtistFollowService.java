package com.superposition.follow.service;

import com.superposition.follow.dto.ArtistFollowDto;
import com.superposition.artist.dto.ArtistInfo;
import java.util.List;

public interface ArtistFollowService {
    void followArtist(ArtistFollowDto dto);

    List<ArtistInfo> getFollowArtistsBy(String email);

    void deleteArtistFollow(ArtistFollowDto dto);
}
