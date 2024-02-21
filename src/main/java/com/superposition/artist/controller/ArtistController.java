package com.superposition.artist.controller;

import com.superposition.artist.dto.ResponseArtistDetail;
import com.superposition.artist.dto.ResponseDisplayArtist;
import com.superposition.artist.service.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/about")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseDisplayArtist> getAboutArtist() {
        return artistService.getAboutArtist();
    }

    @GetMapping("/artist")
    @ResponseStatus(HttpStatus.OK)
    public List<? extends ResponseDisplayArtist> getAllArtist(
            @RequestParam(value = "search", defaultValue = " ") String search,
            @RequestParam(value = "isProductPage", defaultValue = "false") boolean isProductPage) {
        return artistService.getAllArtist(search.trim(), isProductPage);
    }

    @GetMapping("/artist/{instagramId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseArtistDetail getArtistByName(@PathVariable String instagramId) {
        return artistService.getArtistInfoById(instagramId);
    }

    @PatchMapping("/artist/{instagramId}/view")
    @ResponseStatus(HttpStatus.OK)
    public void addViewCountByName(@PathVariable String instagramId) {
        artistService.addViewCountById(instagramId);
    }
}
