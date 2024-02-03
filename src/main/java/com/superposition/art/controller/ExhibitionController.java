package com.superposition.art.controller;

import com.superposition.art.domain.entity.ExhibitionStatus;
import com.superposition.art.dto.ResponseExhibition;
import com.superposition.art.dto.ResponseExhibitionDetail;
import com.superposition.art.service.ExhibitionService;
import com.superposition.product.utils.PageInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/exhibitions")
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    //    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
    public PageInfo<ResponseExhibition> getExhibitions(@PageableDefault(page = 1, size = 10) Pageable pageable,
                                                       @RequestParam(required = false) ExhibitionStatus status) {

        return exhibitionService.getExhibitions(pageable, status);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseExhibition> getExhibitionsByStatus(@RequestParam(required = false) ExhibitionStatus status) {

        return exhibitionService.getExhibitionsByStatus(status);
    }

    @GetMapping("/{exhibitionId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseExhibitionDetail getExhibitionById(@PathVariable long exhibitionId) {
        return exhibitionService.getExhibitionById(exhibitionId);
    }
}
