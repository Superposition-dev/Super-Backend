package com.superposition.art.controller;

import com.superposition.art.domain.entity.ExhibitionStatus;
import com.superposition.art.dto.ResponseExhibition;
import com.superposition.art.service.ExhibitionService;
import com.superposition.product.utils.PageInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exhibitions")
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    public ExhibitionController(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageInfo<ResponseExhibition> getExhibitions(@PageableDefault(page = 1, size = 10) Pageable pageable,
                                                       @RequestParam(required = false) ExhibitionStatus status) {

        return exhibitionService.getExhibitions(pageable, status);
    }

    @GetMapping("/{exhibitionId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseExhibition getExhibitionById(@PathVariable long exhibitionId) {
        return exhibitionService.getExhibitionById(exhibitionId);
    }
}
