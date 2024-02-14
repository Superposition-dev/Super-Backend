package com.superposition.artist.service;

import com.superposition.artist.domain.entity.ArtistFollow;
import com.superposition.artist.domain.mapper.ArtistFollowMapper;
import com.superposition.artist.domain.mapper.ArtistMapper;
import com.superposition.artist.dto.ArtistFollowDto;
import com.superposition.artist.dto.ArtistInfo;
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
    private final ArtistFollowMapper artistFollowMapper;

    @Override
    public void followArtist(ArtistFollowDto dto) {
        checkExistsUser(dto.getEmail());
        checkExistsArtist(dto.getInstagramId());
        checkAlreadyFollow(dto);

        artistFollowMapper.save(dto);
    }

    private void checkExistsUser(String email) {
        if (!userMapper.isExistUserByEmail(email)) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }
    }

    private void checkExistsArtist(String instagramId) {
        if (!artistMapper.isExistsArtist(instagramId)) {
            throw new IllegalArgumentException("존재하지 않는 작가입니다.");
        }
    }

    private void checkAlreadyFollow(ArtistFollowDto dto) {
        if (artistFollowMapper.isExistFollowBy(dto)) {
            throw new IllegalArgumentException("이미 팔로우한 작가입니다.");
        }
    }

    @Override
    public List<ArtistInfo> getFollowArtistsBy(String email) {
        checkExistsUser(email);

        List<String> followInstagramIds = artistFollowMapper.findByEmail(email).stream()
                .map(ArtistFollow::getInstagramId)
                .toList();

        if (followInstagramIds.isEmpty()) {
            return Collections.emptyList();
        }

        return artistMapper.getArtistsInfoByIds(followInstagramIds);
    }
}
