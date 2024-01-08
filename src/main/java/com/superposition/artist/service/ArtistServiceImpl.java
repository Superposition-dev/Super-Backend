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
    public List<ResponseDisplayArtist> getAboutArtist() {
        return artistMapper.getAboutArtist();
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
    public ResponseArtistDetail getArtistInfoById(String instagramId) {
        if(isExistsArtist(instagramId)){
            return dtoToResponse(artistMapper.getArtistInfoById(instagramId));
        } else {
            throw new NoExistArtistException();
        }
    }

    @Override
    public void addViewCountById(String instagramId) {
        if (isExistsArtist(instagramId)){
            artistMapper.addViewCountById(instagramId);
        } else {
            throw new NoExistArtistException();
        }
    }

    private void checkRequest(String search, boolean isProductPage){
        if (!search.isBlank() && isProductPage) throw new BadRequestException();
    }

    private boolean isExistsArtist(String instagramId){
        return artistMapper.isExistsArtist(instagramId);
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

    private ResponseArtistDetail dtoToResponse(ArtistInfo dto){
        return ResponseArtistDetail.builder()
                .profile(dto.getProfile())
                .name(dto.getName())
                .introduce(dto.getIntroduce())
                .isDisplay(dto.isDisplay())
                .description(dto.getDescription())
                .products(productService.getProductByName(dto.getName()))
                .instagramId(dto.getInstagramId()).build();
    }
}
