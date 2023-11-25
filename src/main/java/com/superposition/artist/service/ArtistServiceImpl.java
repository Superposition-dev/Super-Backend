package com.superposition.artist.service;

import com.superposition.artist.domain.mapper.ArtistMapper;
import com.superposition.artist.dto.ArtistInfo;
import com.superposition.artist.dto.ResponseArtist;
import com.superposition.artist.dto.ResponseArtistDetail;
import com.superposition.artist.dto.ResponseDisplayArtist;
import com.superposition.artist.exception.BadRequestException;
import com.superposition.artist.exception.NoExistArtistException;
import com.superposition.product.service.ProductService;
import com.superposition.utils.exception.NoSearchException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService{
    private final ArtistMapper artistMapper;
    private final ProductService productService;

    public ArtistServiceImpl(ArtistMapper artistMapper, ProductService productService) {
        this.artistMapper = artistMapper;
        this.productService = productService;
    }

    @Override
    public List<? extends ResponseDisplayArtist> getAllArtist(String search, boolean isProductPage) {
        checkRequest(search, isProductPage);

        if(isProductPage) return artistMapper.getDisplayArtist();

        if(!search.isBlank()) {
            return searchByKeyword(search);
        } else {
            return artistMapper.getAllArtist();
        }
    }

    @Override
    public ResponseArtistDetail getArtistInfoByName(String name) {
        if(isExistsArtist(name)){
            return dtoToResponse(artistMapper.getArtistInfoByName(name), name);
        } else {
            throw new NoExistArtistException();
        }
    }

    @Override
    public void addViewCountByName(String name) {
        if (isExistsArtist(name)){
            artistMapper.addViewCountByName(name);
        } else {
            throw new NoExistArtistException();
        }
    }

    private void checkRequest(String search, boolean isProductPage){
        if (!search.isBlank() && isProductPage) throw new BadRequestException();
    }

    private boolean isExistsArtist(String name){
        return artistMapper.isExistsArtist(name);
    }

    private List<ResponseArtist> searchByKeyword(String keyword){
        if(isExistsResult(keyword)){
            return artistMapper.getArtistByKeyword(keyword);
        } else {
            throw new NoSearchException();
        }
    }

    private boolean isExistsResult(String keyword){
        return artistMapper.isExistsResult(keyword);
    }

    private ResponseArtistDetail dtoToResponse(ArtistInfo dto, String name){
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
