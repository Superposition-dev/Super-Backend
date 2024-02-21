package com.superposition.artist.controller;

import com.superposition.artist.dto.ResponseArtistDetail;
import com.superposition.artist.dto.ResponseDisplayArtist;
import com.superposition.artist.service.ArtistService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
