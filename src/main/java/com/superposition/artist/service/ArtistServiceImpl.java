package com.superposition.artist.service;

import com.superposition.artist.domain.mapper.ArtistMapper;
import com.superposition.artist.dto.ResponseArtist;
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
    public List<? extends ResponseDisplayArtist> getAllArtist(String keyword, boolean isProductPage) {
        if(isProductPage) return artistMapper.getDisplayArtist();

        if(!keyword.trim().isBlank()) {
            return searchByKeyword(keyword);
        } else {
            return artistMapper.getAllArtist();
        }
    }

    @Override
    public ResponseArtistDetail getArtistByName(String name) {
        return artistMapper.getArtistByName(name);
    }

    private List<ResponseArtist> searchByKeyword(String keyword){
        return artistMapper.getArtistByKeyword(keyword);
    }
}
