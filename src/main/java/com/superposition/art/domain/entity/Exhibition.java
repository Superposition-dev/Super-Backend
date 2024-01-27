package com.superposition.art.domain.entity;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Exhibition {

    private final Long id;
    private final String title;
    private final String subheading;
    private final String location;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final ExhibitionStatus status;
    private final String poster;

    @Builder
    public Exhibition(Long id, String title, String subheading, String location, LocalDate startDate, LocalDate endDate,
                      ExhibitionStatus status, String poster) {
        this.id = id;
        this.title = title;
        this.subheading = subheading;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.poster = poster;
    }
}
