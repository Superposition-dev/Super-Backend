package com.superposition.artist.service;

import com.superposition.artist.dto.ResponseArtistDetail;
import com.superposition.artist.dto.ResponseDisplayArtist;

import java.util.List;

public interface ArtistService {
    public List<ResponseDisplayArtist> getAboutArtist();
    public List<? extends ResponseDisplayArtist> getAllArtist(String search, boolean isProductPage);
    public ResponseArtistDetail getArtistInfoById(String instagramId);
    public void addViewCountById(String instagramId);
}
