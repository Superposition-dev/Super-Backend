package com.superposition.artist.service;

import com.superposition.artist.domain.mapper.ArtistMapper;
import com.superposition.artist.dto.ArtistFollowDto;
import com.superposition.artist.dto.ArtistInfo;
import com.superposition.user.domain.entity.User;
import com.superposition.user.domain.mapper.UserMapper;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtistFollowServiceImpl implements ArtistFollowService {

    private final UserMapper userMapper;
    private final ArtistMapper artistMapper;

    @Override
    public void followArtist(ArtistFollowDto dto) {
        String userEmail = dto.getUserEmail();
        User findUser = userMapper.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        String artistInstagramId = dto.getArtistInstagramId();
        checkExistsArtist(artistInstagramId);

        checkAlreadyFollowing(findUser, artistInstagramId);

        userMapper.updateFollow(userEmail, getNewFollowing(artistInstagramId, findUser));
    }

    private void checkExistsArtist(String artistInstagramId) {
        boolean existsArtist = artistMapper.isExistsArtist(artistInstagramId);
        if (!existsArtist) {
            throw new IllegalArgumentException("존재하지 않는 작가입니다.");
        }
    }

    private void checkAlreadyFollowing(User findUser, String artistInstagramId) {
        if (findUser.getFollowing() != null && findUser.isFollowed(artistInstagramId)) {
            throw new IllegalArgumentException("이미 팔로우한 작가입니다.");
        }
    }

    private String getNewFollowing(String artistInstagramId, User findUser) {
        String existsFollowing = findUser.getFollowing();
        if (existsFollowing == null) {
            return artistInstagramId;
        }

        return existsFollowing + "," + artistInstagramId;
    }

    @Override
    public List<ArtistInfo> getFollowingArtists(String userEmail) {
        User findUser = userMapper.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        if (findUser.isEmptyFollowing()) {
            return Collections.emptyList();
        }
        List<String> followingArtistInstagramIds = findUser.getFollowingArtistInstagramIds();
        return artistMapper.getArtistsInfoByIds(followingArtistInstagramIds);
    }
}
