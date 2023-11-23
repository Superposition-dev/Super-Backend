package com.superposition.artist.service;

import com.superposition.artist.domain.mapper.ArtistMapper;
import com.superposition.artist.dto.ResponseArtistDetail;
import com.superposition.artist.dto.ResponseDisplayArtist;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService{
    private final ArtistMapper artistMapper;

    public ArtistServiceImpl(ArtistMapper artistMapper) {
        this.artistMapper = artistMapper;
    }

    @Override
    public List<? extends ResponseDisplayArtist> getAllArtist(boolean isProductPage) {
        if(isProductPage){
            return artistMapper.getDisplayArtist();
        } else {
            return artistMapper.getAllArtist();
        }
    }

    @Override
    public ResponseArtistDetail getArtistByName(String name) {
        return artistMapper.getArtistByName(name);
    }
}
