package com.superposition.art.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

import com.superposition.art.domain.entity.ExhibitionStatus;
import com.superposition.art.dto.ResponseExhibition;
import com.superposition.art.dto.ResponseExhibitionDetail;
import com.superposition.art.exception.NoExistExhibitionException;
import com.superposition.product.utils.PageInfo;
import java.time.LocalDate;
import java.util.List;
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
    @DisplayName("모든 전시 조회 테스트")
    void getExhibitionsByStatusTest() {
        //given
        ExhibitionStatus status = null;

        //when
        List<ResponseExhibition> actual = exhibitionService.getExhibitionsByStatus(status);

        //then
        assertThat(actual)
                .hasSize(15)
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
                                "cypresses.jpg"),
                        tuple("Art for the Millions: American Culture and Politics in the 1930s",
                                "The 1930s was a decade of political and social upheaval in the United States,", "MET",
                                LocalDate.parse("2023-09-07"), LocalDate.parse("2023-12-10"),
                                ExhibitionStatus.end.getValue(),
                                "1930s.jpg"),
                        tuple("Picasso: A Cubist Commission in Brooklyn",
                                "In 1910 Pablo Picasso (1881–1973) embarked on a decorative commission for the Brooklyn residence of artist",
                                "MET",
                                LocalDate.parse("2023-09-14"), LocalDate.parse("2024-01-14"),
                                ExhibitionStatus.end.getValue(),
                                "Picasso.jpg"),
                        tuple("Vertigo of Color: Matisse, Derain, and the Origins of Fauvism",
                                "The exhibition is made possible by The Florence Gould Foundation.", "MET",
                                LocalDate.parse("2023-10-13"), LocalDate.parse("2024-01-21"),
                                ExhibitionStatus.end.getValue(),
                                "Fauvism.jpg"),
                        tuple("Proof: Maxime Du Camp’s Photographs of the Eastern Mediterranean and North Africa",
                                "Officially encouraged to exploit photography’s “uncontestable exactitude,", "MET",
                                LocalDate.parse("2023-10-23"), LocalDate.parse("2024-01-21"),
                                ExhibitionStatus.end.getValue(),
                                "Mediterranean.jpg"),
                        tuple("Christmas Tree and Neapolitan Baroque Crèche",
                                "The Met continues a longstanding holiday tradition with the presentation of its Christmas tree.",
                                "MET",
                                LocalDate.parse("2023-11-21"), LocalDate.parse("2024-01-07"),
                                ExhibitionStatus.end.getValue(),
                                "tree.jpg"));
    }

    @Test
    @DisplayName("전시중 전시들 조회 테스트")
    void getCurrentExhibitionsByStatusTest() {
        //given
        ExhibitionStatus status = ExhibitionStatus.end;

        //when
        List<ResponseExhibition> actual = exhibitionService.getExhibitionsByStatus(status);

        //then
        assertThat(actual)
                .hasSize(6)
                .extracting("title", "subHeading", "location", "startDate", "endDate", "status", "poster")
                .containsExactlyInAnyOrder(
                        tuple("Van Gogh’s Cypresses",
                                "Van Gogh’s Cypresses is the first exhibition to focus on the trees", "MET",
                                LocalDate.parse("2023-05-22"), LocalDate.parse("2023-08-27"),
                                ExhibitionStatus.end.getValue(),
                                "cypresses.jpg"),
                        tuple("Art for the Millions: American Culture and Politics in the 1930s",
                                "The 1930s was a decade of political and social upheaval in the United States,", "MET",
                                LocalDate.parse("2023-09-07"), LocalDate.parse("2023-12-10"),
                                ExhibitionStatus.end.getValue(),
                                "1930s.jpg"),
                        tuple("Picasso: A Cubist Commission in Brooklyn",
                                "In 1910 Pablo Picasso (1881–1973) embarked on a decorative commission for the Brooklyn residence of artist",
                                "MET",
                                LocalDate.parse("2023-09-14"), LocalDate.parse("2024-01-14"),
                                ExhibitionStatus.end.getValue(),
                                "Picasso.jpg"),
                        tuple("Vertigo of Color: Matisse, Derain, and the Origins of Fauvism",
                                "The exhibition is made possible by The Florence Gould Foundation.", "MET",
                                LocalDate.parse("2023-10-13"), LocalDate.parse("2024-01-21"),
                                ExhibitionStatus.end.getValue(),
                                "Fauvism.jpg"),
                        tuple("Proof: Maxime Du Camp’s Photographs of the Eastern Mediterranean and North Africa",
                                "Officially encouraged to exploit photography’s “uncontestable exactitude,", "MET",
                                LocalDate.parse("2023-10-23"), LocalDate.parse("2024-01-21"),
                                ExhibitionStatus.end.getValue(),
                                "Mediterranean.jpg"),
                        tuple("Christmas Tree and Neapolitan Baroque Crèche",
                                "The Met continues a longstanding holiday tradition with the presentation of its Christmas tree.",
                                "MET",
                                LocalDate.parse("2023-11-21"), LocalDate.parse("2024-01-07"),
                                ExhibitionStatus.end.getValue(),
                                "tree.jpg"));
    }

    @Test
    @DisplayName("전시 종료 전시들 조회 테스트")
    void getEndExhibitionsByStatusTest() {
        //given
        ExhibitionStatus status = ExhibitionStatus.current;

        //when
        List<ResponseExhibition> actual = exhibitionService.getExhibitionsByStatus(status);

        //then
        assertThat(actual)
                .hasSize(6)
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
                                "Lineages.jpg"));
    }

    @Test
    @DisplayName("전시 ID로 전시 조회 테스트")
    void getExhibitionByIdTest() {
        //given
        long exhibitionId = 1L;

        //when
        ResponseExhibitionDetail actual = exhibitionService.getExhibitionById(exhibitionId);

        //then
        assertThat(actual.getExhibitionId()).isEqualTo(exhibitionId);
        assertThat(actual.getTitle()).isEqualTo("Christmas Tree and Neapolitan Baroque Crèche");
        assertThat(actual.getSubHeading()).isEqualTo(
                "The Met continues a longstanding holiday tradition with the presentation of its Christmas tree.");
        assertThat(actual.getProductInfo())
                .hasSize(3)
                .extracting("productId", "picture", "tags", "title", "artist")
                .containsExactlyInAnyOrder(
                        tuple(1L, "6e305a70-0ed7-4f40-a59e-e28bb495887d.jpg", new String[]{"청량한", "맑은", "아련한"},
                                "roses", "문소"),
                        tuple(13L, "e58b394b-c0dd-4992-be70-3a258a1fc061.png", new String[]{"따뜻한", "귀여운", "포근한"},
                                "christmas in my hand", "삼이공"),
                        tuple(20L, "14fcf569-2c1b-482f-93cf-a78c65b01aa5.jpg", new String[]{"웅장한", "묘한", "상징적인"},
                                "You shine, protect the light", "윤지호"));

        assertThat(actual.getArtistInfo())
                .hasSize(3)
                .extracting("name", "introduce", "instagramId")
                .containsExactlyInAnyOrder(
                        tuple("문소", "우리의 모습을 그립니다", "moonso___"),
                        tuple("삼이공", "다른 세계의 희망을 보려 도약하는", "320_artwork"),
                        tuple("윤지호", "긍정을 제시하는 표지판", "yjh_hall"));
    }

    @Test
    @DisplayName("존재하지 않는 전시 ID로 전시 조회 테스트")
    void getExhibitionByNotExistIdTest() {
        //given
        long notExistsExhibitionId = 1000L;

        //expected
        assertThatThrownBy(() -> exhibitionService.getExhibitionById(notExistsExhibitionId))
                .isInstanceOf(NoExistExhibitionException.class)
                .hasMessageContaining("해당하는 게시물이 없습니다.");
    }
}