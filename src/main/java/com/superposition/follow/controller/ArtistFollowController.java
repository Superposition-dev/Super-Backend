package com.superposition.follow.controller;

import com.superposition.follow.dto.ArtistFollowDto;
import com.superposition.artist.dto.ArtistInfo;
import com.superposition.follow.service.ArtistFollowService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistFollowController {

    private final ArtistFollowService artistFollowService;

    @GetMapping(value = "/follow")
    @ResponseStatus(HttpStatus.OK)
    public List<? extends ArtistInfo> getFollowArtists(@AuthenticationPrincipal UserDetails user) {
        return artistFollowService.getFollowArtistsBy(user.getUsername());
    }

    @PostMapping("/{instagramId}/follow")
    @ResponseStatus(HttpStatus.OK)
    public void followArtist(@AuthenticationPrincipal UserDetails user, @PathVariable String instagramId) {
        ArtistFollowDto dto = ArtistFollowDto.builder()
                .email(user.getUsername())
                .instagramId(instagramId)
                .build();
        artistFollowService.followArtist(dto);
    }
}
