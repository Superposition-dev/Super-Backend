package com.superposition.art.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.superposition.art.domain.entity.ExhibitionStatus;
import com.superposition.art.dto.ResponseExhibition;
import com.superposition.product.utils.PageInfo;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class ExhibitionServiceTest {

    @Autowired
    private ExhibitionService exhibitionService;

    @Test
    @DisplayName("전시 조회 테스트")
    void getExhibitionsTest() {
        //given
        Pageable pageable = PageRequest.of(1, 10);
        ExhibitionStatus status = null;

        //when
        PageInfo<ResponseExhibition> actual = exhibitionService.getExhibitions(pageable, status);

        //then
        assertThat(actual).extracting("pageIndex", "pageSize", "totalCount")
                .contains(1, 10, 15);
        assertThat(actual.getData())
                .hasSize(10)
                .extracting("title", "subHeading", "location", "startDate", "endDate", "status", "poster")
                .containsExactlyInAnyOrder(
                        tuple("Women Dressing Women",
                                "The exhibition and catalogue are made possible by Morgan Stanley.", "MET",
                                LocalDate.parse("2023-12-16"), LocalDate.parse("2024-03-03"),
                                ExhibitionStatus.CURRENT.getValue(),
                                "women.jpg"),
                        tuple("Africa & Byzantium",
                                "The exhibition is made possible by the Ford Foundation, The Giorgi Family Foundation, and Mary Jaharis.",
                                "MET", LocalDate.parse("2023-12-16"), LocalDate.parse("2024-03-03"),
                                ExhibitionStatus.CURRENT.getValue(), "africa.jpg"),
                        tuple("여기는 따뜻해", "성수지앵XSuperposition 겨울 전시", "성수지앵", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2023-12-25"), ExhibitionStatus.CURRENT.getValue(),
                                "dsafsdafsdfaroghg.jpg"),
                        tuple("여기는 따뜻해", "성수지앵XSuperposition 겨울 전시", "성수지앵", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2023-12-25"), ExhibitionStatus.CURRENT.getValue(),
                                "dsafsdafsdfaroghg.jpg"),
                        tuple("여기는 따뜻해", "성수지앵XSuperposition 겨울 전시", "성수지앵", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2023-12-25"), ExhibitionStatus.CURRENT.getValue(),
                                "dsafsdafsdfaroghg.jpg"),
                        tuple("여기는 따뜻해", "성수지앵XSuperposition 겨울 전시", "성수지앵", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2023-12-25"), ExhibitionStatus.CURRENT.getValue(),
                                "dsafsdafsdfaroghg.jpg"),
                        tuple("Grounded in Clay: The Spirit of Pueblo Pottery",
                                "Pueblo Indian pottery embodies four main natural elements", "MET",
                                LocalDate.parse("2023-12-16"), LocalDate.parse("2024-06-04"),
                                ExhibitionStatus.CURRENT.getValue(),
                                "Pottery.jpg"),
                        tuple("The Facade Commission: Nairy Baghramian, Scratching the Back",
                                "For The Met Fifth Avenue’s facade niches,", "MET", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2024-05-28"), ExhibitionStatus.CURRENT.getValue(), "commission.jpg"),
                        tuple("Lineages: Korean Art at The Met", "METXSuperposition 겨울 전시", "MET",
                                LocalDate.parse("2023-12-16"), LocalDate.parse("2023-12-25"),
                                ExhibitionStatus.CURRENT.getValue(),
                                "Lineages.jpg"),
                        tuple("Van Gogh’s Cypresses",
                                "Van Gogh’s Cypresses is the first exhibition to focus on the trees", "MET",
                                LocalDate.parse("2023-05-22"), LocalDate.parse("2023-08-27"),
                                ExhibitionStatus.END.getValue(),
                                "cypresses.jpg"));
    }
}