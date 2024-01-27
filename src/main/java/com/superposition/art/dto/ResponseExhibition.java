package com.superposition.art.dto;

import com.superposition.art.domain.entity.Exhibition;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseExhibition {

    private final Long exhibitionId;
    private final String title;
    private final String subheading;
    private final String location;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String status;
    private final String poster;

    @Builder
    public ResponseExhibition(Long exhibitionId, String title, String subheading, String location, LocalDate startDate,
                              LocalDate endDate, String status, String poster) {

        this.exhibitionId = exhibitionId;
        this.title = title;
        this.subheading = subheading;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.poster = poster;
    }

    public static ResponseExhibition from(final Exhibition exhibition) {
        return ResponseExhibition.builder()
                .exhibitionId(exhibition.getId())
                .title(exhibition.getTitle())
                .subheading(exhibition.getSubheading())
                .location(exhibition.getLocation())
                .startDate(exhibition.getStartDate())
                .endDate(exhibition.getEndDate())
                .status(exhibition.getStatus().getValue())
                .poster(exhibition.getPoster())
                .build();
    }
}
