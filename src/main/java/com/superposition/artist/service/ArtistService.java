package com.superposition.artist.service;

import com.superposition.artist.dto.ResponseArtistDetail;
import com.superposition.artist.dto.ResponseDisplayArtist;

import java.util.List;

public interface ArtistService {
    public List<? extends ResponseDisplayArtist> getAllArtist(boolean isProductPage);
    public ResponseArtistDetail getArtistByName(String name);

}
