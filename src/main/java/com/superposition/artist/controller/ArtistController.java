package com.superposition.artist.controller;

import com.superposition.artist.dto.ResponseArtistDetail;
import com.superposition.artist.dto.ResponseDisplayArtist;
import com.superposition.artist.service.ArtistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/artist")
    public List<? extends ResponseDisplayArtist> getAllArtist(@RequestParam(value = "search", defaultValue = " ") String search, @RequestParam(value = "isProductPage", defaultValue = "false") boolean isProductPage){
        return artistService.getAllArtist(search, isProductPage);
    }

    @GetMapping("/artist/{artistName}")
    public ResponseArtistDetail getArtistByName(@PathVariable String artistName){
        return artistService.getArtistInfoByName(artistName);
    }
}
