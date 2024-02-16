package com.superposition.user.controller;

import com.superposition.artist.dto.ArtistInfo;
import com.superposition.artist.service.ArtistFollowService;
import com.superposition.user.dto.CurrentUser;
import com.superposition.user.dto.RequestUserInfo;
import com.superposition.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ArtistFollowService artistFollowService;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid RequestUserInfo userInfo){
        return userService.signup(userInfo);
    }

    @GetMapping(value = "/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code){
        return userService.loginByKakao(code);
    }

    @GetMapping(value = "/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@CurrentUser String email){
        userService.logout(email);
    }

    @DeleteMapping
    public void deleteUser(@CurrentUser String email){
        userService.deleteUser(email);
    }

    @GetMapping(value = "/isAvailable")
    @ResponseStatus(HttpStatus.OK)
    public Boolean checkUserNickname(@CurrentUser String email){
        return userService.checkNickname(email);
    }

    @GetMapping(value = "/regenerateToken")
    public ResponseEntity<?> regenerateToken(@CookieValue("Refresh_Token") String rt) {
        return userService.regenerateToken(rt);
    }

    @GetMapping(value = "/artist/follow")
    @ResponseStatus(HttpStatus.OK)
    public List<? extends ArtistInfo> getFollowArtists(@CurrentUser String email) {
        return artistFollowService.getFollowArtistsBy(email);
    }
}
