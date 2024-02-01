package com.superposition.art.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.superposition.art.domain.entity.ExhibitionStatus;
import com.superposition.art.dto.ResponseExhibition;
import com.superposition.art.exception.NoExistExhibitionException;
import com.superposition.product.utils.PageInfo;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

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
                                ExhibitionStatus.current.getValue(),
                                "women.jpg"),
                        tuple("Africa & Byzantium",
                                "The exhibition is made possible by the Ford Foundation, The Giorgi Family Foundation, and Mary Jaharis.",
                                "MET", LocalDate.parse("2023-12-16"), LocalDate.parse("2024-03-03"),
                                ExhibitionStatus.current.getValue(), "africa.jpg"),
                        tuple("여기는 따뜻해", "성수지앵XSuperposition 겨울 전시", "성수지앵", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2023-12-25"), ExhibitionStatus.current.getValue(),
                                "dsafsdafsdfaroghg.jpg"),
                        tuple("여기는 따뜻해", "성수지앵XSuperposition 겨울 전시", "성수지앵", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2023-12-25"), ExhibitionStatus.current.getValue(),
                                "dsafsdafsdfaroghg.jpg"),
                        tuple("여기는 따뜻해", "성수지앵XSuperposition 겨울 전시", "성수지앵", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2023-12-25"), ExhibitionStatus.current.getValue(),
                                "dsafsdafsdfaroghg.jpg"),
                        tuple("여기는 따뜻해", "성수지앵XSuperposition 겨울 전시", "성수지앵", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2023-12-25"), ExhibitionStatus.current.getValue(),
                                "dsafsdafsdfaroghg.jpg"),
                        tuple("Grounded in Clay: The Spirit of Pueblo Pottery",
                                "Pueblo Indian pottery embodies four main natural elements", "MET",
                                LocalDate.parse("2023-12-16"), LocalDate.parse("2024-06-04"),
                                ExhibitionStatus.current.getValue(),
                                "Pottery.jpg"),
                        tuple("The Facade Commission: Nairy Baghramian, Scratching the Back",
                                "For The Met Fifth Avenue’s facade niches,", "MET", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2024-05-28"), ExhibitionStatus.current.getValue(), "commission.jpg"),
                        tuple("Lineages: Korean Art at The Met", "METXSuperposition 겨울 전시", "MET",
                                LocalDate.parse("2023-12-16"), LocalDate.parse("2023-12-25"),
                                ExhibitionStatus.current.getValue(),
                                "Lineages.jpg"),
                        tuple("Van Gogh’s Cypresses",
                                "Van Gogh’s Cypresses is the first exhibition to focus on the trees", "MET",
                                LocalDate.parse("2023-05-22"), LocalDate.parse("2023-08-27"),
                                ExhibitionStatus.end.getValue(),
                                "cypresses.jpg"));
    }

    @Test
    @DisplayName("전시 ID로 전시 조회 테스트")
    @Sql({"classpath:schema.sql", "classpath:data.sql"})
    void getExhibitionByIdTest() {
        //given
        long exhibitionId = 1L;

        //when
        ResponseExhibition actual = exhibitionService.getExhibitionById(exhibitionId);

        //then
        ResponseExhibition expected = ResponseExhibition.builder()
                .exhibitionId(exhibitionId)
                .title("Christmas Tree and Neapolitan Baroque Crèche")
                .subHeading(
                        "The Met continues a longstanding holiday tradition with the presentation of its Christmas tree.")
                .location("MET")
                .startDate(LocalDate.parse("2023-11-21"))
                .endDate(LocalDate.parse("2024-01-07"))
                .status(ExhibitionStatus.end.getValue())
                .poster("tree.jpg")
                .build();
        assertAll(() -> {
            assertThat(actual.getExhibitionId()).isEqualTo(expected.getExhibitionId());
            assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
            assertThat(actual.getSubHeading()).isEqualTo(expected.getSubHeading());
            assertThat(actual.getLocation()).isEqualTo(expected.getLocation());
            assertThat(actual.getStartDate()).isEqualTo(expected.getStartDate());
            assertThat(actual.getEndDate()).isEqualTo(expected.getEndDate());
            assertThat(actual.getStatus()).isEqualTo(expected.getStatus());
            assertThat(actual.getPoster()).isEqualTo(expected.getPoster());
        });
    }

    @Test
    @DisplayName("존재하지 않는 전시 ID로 전시 조회 테스트")
    @Sql({"classpath:schema.sql", "classpath:data.sql"})
    void getExhibitionByNotExistIdTest() {
        //given
        long notExistsExhibitionId = 1000L;

        //expected
        assertThatThrownBy(() -> exhibitionService.getExhibitionById(notExistsExhibitionId))
                .isInstanceOf(NoExistExhibitionException.class)
                .hasMessageContaining("해당하는 게시물이 없습니다.");
    }
}