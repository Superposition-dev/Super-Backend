package com.superposition.artist.service;

import com.superposition.artist.domain.mapper.ArtistMapper;
import com.superposition.artist.dto.ArtistDto;
import com.superposition.artist.dto.ResponseArtist;
import com.superposition.artist.dto.ResponseArtistDetail;
import com.superposition.artist.dto.ResponseDisplayArtist;
import com.superposition.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArtistServiceImpl implements ArtistService{
    private final ArtistMapper artistMapper;
    private final ProductService productService;

    public ArtistServiceImpl(ArtistMapper artistMapper, ProductService productService) {
        this.artistMapper = artistMapper;
        this.productService = productService;
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
        return dtoToResponse(artistMapper.getArtistByName(name), name);
    }

    private List<ResponseArtist> searchByKeyword(String keyword){
        return artistMapper.getArtistByKeyword(keyword);
    }

    private ResponseArtistDetail dtoToResponse(ArtistDto dto, String name){
        return ResponseArtistDetail.builder()
                .profile(dto.getProfile())
                .name(dto.getName())
                .introduce(dto.getIntroduce())
                .isDisplay(dto.isDisplay())
                .description(dto.getDescription())
                .products(productService.getProductByName(name))
                .instaId(dto.getInstagramId()).build();
    }
}
