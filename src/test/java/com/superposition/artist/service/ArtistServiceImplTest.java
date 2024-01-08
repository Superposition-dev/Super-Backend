package com.superposition.artist.service;

import com.superposition.artist.domain.mapper.ArtistMapper;
import com.superposition.artist.dto.ResponseArtistDetail;
import com.superposition.artist.dto.ResponseDisplayArtist;
import com.superposition.artist.exception.BadRequestException;
import com.superposition.artist.exception.NoExistArtistException;
import com.superposition.utils.exception.NoSearchException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ArtistServiceImplTest {
    private final ArtistService artistService;
    private final ArtistMapper artistMapper;

    @Autowired

    public ArtistServiceImplTest(ArtistService artistService, ArtistMapper artistMapper) {
        this.artistService = artistService;
        this.artistMapper = artistMapper;
    }

    @Test
    @DisplayName("작가 페이지 목록")
    void 작가_조회1() {
        //given
        String search = " ";
        boolean isProductPage = false;

        //when
        List<? extends ResponseDisplayArtist> allArtist = artistService.getAllArtist(search, isProductPage);

        //then
        assertThat(allArtist.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("작품 조회 시 전시 작가 목록 호출")
    void 작가_조회2() {
        //given
        String search = " ";
        boolean isProductPage = true;

        //when
        List<? extends ResponseDisplayArtist> allArtist = artistService.getAllArtist(search, isProductPage);

        //then
        assertThat(allArtist.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("검색어 O")
    void 작가_검색_조회() {
        //given
        String search = "바";
        boolean isProductPage = false;

        //when
        List<? extends ResponseDisplayArtist> allArtist = artistService.getAllArtist(search, isProductPage);

        //then
        assertThat(allArtist.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("검색 결과가 없는 경우")
    void 작가_검색_조회_실패() {
        //given
        String search = "test";
        boolean isProductPage = false;

        //when
        NoSearchException noSearchException
                = assertThrows(NoSearchException.class, () -> artistService.getAllArtist(search, isProductPage));

        //then
        assertThat(noSearchException.getClass()).isEqualTo(NoSearchException.class);
    }

    @Test
    @DisplayName("검색어 + 제품 페이지 -> 잘못된 요청")
    void 작가_조회_실패_테스트() {
        //given
        String search = "바";
        boolean isProductPage = true;

        //when
        BadRequestException badRequestException
                = assertThrows(BadRequestException.class, () -> artistService.getAllArtist(search, isProductPage));

        //then
        assertThat(badRequestException.getClass()).isEqualTo(BadRequestException.class);
    }

    @Test
    @DisplayName("작가 ID로 조회")
    void 작가_조회() {
        //given
        String id = "bakyun.artsy";

        //when
        ResponseArtistDetail info = artistService.getArtistInfoById(id);
        System.out.println("info = " + info);

        //then
        assertThat(info.getInstagramId()).isEqualTo(id);
    }

    @Test
    @DisplayName("작가가 없는 경우")
    void 작가_조회_실패() {
        //given
        String name = "test";

        //when
        NoExistArtistException noExistArtistException =
                assertThrows(NoExistArtistException.class, () -> artistService.getArtistInfoById(name));

        //then
        assertThat(noExistArtistException.getClass()).isEqualTo(NoExistArtistException.class);
    }

    @Test
    @DisplayName("작가 조회수 추가")
    void 조회수_테스트() {
        //given
        String name = "바켠";

        //when
        artistService.addViewCountById(name);

        //then
        assertThat(artistMapper.getViewCount(name)).isEqualTo(1);
    }

    @Test
    @DisplayName("없는 작가 조회수 추가")
    void 조회수_테스트_실패() {
        //given
        String name = "test";

        //when
        NoExistArtistException noExistArtistException =
                assertThrows(NoExistArtistException.class, () -> artistService.addViewCountById(name));

        //then
        assertThat(noExistArtistException.getClass()).isEqualTo(NoExistArtistException.class);
    }
}