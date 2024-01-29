package com.superposition.art.domain.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.superposition.art.domain.entity.Exhibition;
import com.superposition.art.domain.entity.ExhibitionStatus;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
class ExhibitionMapperTest {

    @Autowired
    private ExhibitionMapper exhibitionMapper;

    @Test
    @DisplayName("모든 전시 조회 테스트")
    @Sql({"classpath:schema.sql", "classpath:data.sql"})
    void findExhibitionsTest() {
        //given
        Pageable pageable = PageRequest.of(1, 10);
        int offset = (pageable.getPageNumber() - 1) * pageable.getPageSize();
        int limit = 10;
        ExhibitionStatus status = null;

        //when
        List<Exhibition> actual = exhibitionMapper.findExhibitions(offset, limit, status);

        //then
        assertThat(actual)
                .hasSize(10)
                .extracting("title", "subHeading", "location", "startDate", "endDate", "status", "poster")
                .containsExactlyInAnyOrder(
                        tuple("Women Dressing Women",
                                "The exhibition and catalogue are made possible by Morgan Stanley.", "MET",
                                LocalDate.parse("2023-12-16"), LocalDate.parse("2024-03-03"), ExhibitionStatus.CURRENT,
                                "women.jpg"),
                        tuple("Africa & Byzantium",
                                "The exhibition is made possible by the Ford Foundation, The Giorgi Family Foundation, and Mary Jaharis.",
                                "MET", LocalDate.parse("2023-12-16"), LocalDate.parse("2024-03-03"),
                                ExhibitionStatus.CURRENT, "africa.jpg"),
                        tuple("여기는 따뜻해", "성수지앵XSuperposition 겨울 전시", "성수지앵", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2023-12-25"), ExhibitionStatus.CURRENT, "dsafsdafsdfaroghg.jpg"),
                        tuple("여기는 따뜻해", "성수지앵XSuperposition 겨울 전시", "성수지앵", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2023-12-25"), ExhibitionStatus.CURRENT,
                                "dsafsdafsdfaroghg.jpg"),
                        tuple("여기는 따뜻해", "성수지앵XSuperposition 겨울 전시", "성수지앵", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2023-12-25"), ExhibitionStatus.CURRENT,
                                "dsafsdafsdfaroghg.jpg"),
                        tuple("여기는 따뜻해", "성수지앵XSuperposition 겨울 전시", "성수지앵", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2023-12-25"), ExhibitionStatus.CURRENT,
                                "dsafsdafsdfaroghg.jpg"),
                        tuple("Grounded in Clay: The Spirit of Pueblo Pottery",
                                "Pueblo Indian pottery embodies four main natural elements", "MET",
                                LocalDate.parse("2023-12-16"), LocalDate.parse("2024-06-04"), ExhibitionStatus.CURRENT,
                                "Pottery.jpg"),
                        tuple("The Facade Commission: Nairy Baghramian, Scratching the Back",
                                "For The Met Fifth Avenue’s facade niches,", "MET", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2024-05-28"), ExhibitionStatus.CURRENT, "commission.jpg"),
                        tuple("Lineages: Korean Art at The Met", "METXSuperposition 겨울 전시", "MET",
                                LocalDate.parse("2023-12-16"), LocalDate.parse("2023-12-25"), ExhibitionStatus.CURRENT,
                                "Lineages.jpg"),
                        tuple("Van Gogh’s Cypresses",
                                "Van Gogh’s Cypresses is the first exhibition to focus on the trees", "MET",
                                LocalDate.parse("2023-05-22"), LocalDate.parse("2023-08-27"), ExhibitionStatus.END,
                                "cypresses.jpg"));
    }

    @Test
    @DisplayName("전시중 전시들 조회 테스트")
    @Sql({"classpath:schema.sql", "classpath:data.sql"})
    void findCurrentExhibitionsTest() {
        //given
        Pageable pageable = PageRequest.of(1, 10);
        int offset = (pageable.getPageNumber() - 1) * pageable.getPageSize();
        int limit = 10;
        ExhibitionStatus status = ExhibitionStatus.CURRENT;

        //when
        List<Exhibition> actual = exhibitionMapper.findExhibitions(offset, limit, status);

        //then
        assertThat(actual)
                .hasSize(9)
                .extracting("title", "subHeading", "location", "startDate", "endDate", "status", "poster")
                .containsExactlyInAnyOrder(
                        tuple("Women Dressing Women",
                                "The exhibition and catalogue are made possible by Morgan Stanley.", "MET",
                                LocalDate.parse("2023-12-16"), LocalDate.parse("2024-03-03"), ExhibitionStatus.CURRENT,
                                "women.jpg"),
                        tuple("Africa & Byzantium",
                                "The exhibition is made possible by the Ford Foundation, The Giorgi Family Foundation, and Mary Jaharis.",
                                "MET", LocalDate.parse("2023-12-16"), LocalDate.parse("2024-03-03"),
                                ExhibitionStatus.CURRENT, "africa.jpg"),
                        tuple("여기는 따뜻해", "성수지앵XSuperposition 겨울 전시", "성수지앵", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2023-12-25"), ExhibitionStatus.CURRENT,
                                "dsafsdafsdfaroghg.jpg"),
                        tuple("여기는 따뜻해", "성수지앵XSuperposition 겨울 전시", "성수지앵", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2023-12-25"), ExhibitionStatus.CURRENT,
                                "dsafsdafsdfaroghg.jpg"),
                        tuple("여기는 따뜻해", "성수지앵XSuperposition 겨울 전시", "성수지앵", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2023-12-25"), ExhibitionStatus.CURRENT,
                                "dsafsdafsdfaroghg.jpg"),
                        tuple("여기는 따뜻해", "성수지앵XSuperposition 겨울 전시", "성수지앵", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2023-12-25"), ExhibitionStatus.CURRENT,
                                "dsafsdafsdfaroghg.jpg"),
                        tuple("Grounded in Clay: The Spirit of Pueblo Pottery",
                                "Pueblo Indian pottery embodies four main natural elements", "MET",
                                LocalDate.parse("2023-12-16"), LocalDate.parse("2024-06-04"), ExhibitionStatus.CURRENT,
                                "Pottery.jpg"),
                        tuple("The Facade Commission: Nairy Baghramian, Scratching the Back",
                                "For The Met Fifth Avenue’s facade niches,", "MET", LocalDate.parse("2023-12-16"),
                                LocalDate.parse("2024-05-28"), ExhibitionStatus.CURRENT, "commission.jpg"),
                        tuple("Lineages: Korean Art at The Met", "METXSuperposition 겨울 전시", "MET",
                                LocalDate.parse("2023-12-16"), LocalDate.parse("2023-12-25"), ExhibitionStatus.CURRENT,
                                "Lineages.jpg"));
    }

    @Test
    @DisplayName("전시 종료 전시들 조회 테스트")
    @Sql({"classpath:schema.sql", "classpath:data.sql"})
    void findEndExhibitionsTest() {
        //given
        Pageable pageable = PageRequest.of(1, 10);
        int offset = (pageable.getPageNumber() - 1) * pageable.getPageSize();
        int limit = 10;
        ExhibitionStatus status = ExhibitionStatus.END;

        //when
        List<Exhibition> actual = exhibitionMapper.findExhibitions(offset, limit, status);

        //then
        assertThat(actual)
                .hasSize(6)
                .extracting("title", "subHeading", "location", "startDate", "endDate", "status", "poster")
                .containsExactlyInAnyOrder(
                        tuple("Van Gogh’s Cypresses",
                                "Van Gogh’s Cypresses is the first exhibition to focus on the trees", "MET",
                                LocalDate.parse("2023-05-22"), LocalDate.parse("2023-08-27"), ExhibitionStatus.END,
                                "cypresses.jpg"),
                        tuple("Art for the Millions: American Culture and Politics in the 1930s",
                                "The 1930s was a decade of political and social upheaval in the United States,", "MET",
                                LocalDate.parse("2023-09-07"), LocalDate.parse("2023-12-10"), ExhibitionStatus.END,
                                "1930s.jpg"),
                        tuple("Picasso: A Cubist Commission in Brooklyn",
                                "In 1910 Pablo Picasso (1881–1973) embarked on a decorative commission for the Brooklyn residence of artist",
                                "MET", LocalDate.parse("2023-09-14"), LocalDate.parse("2024-01-14"),
                                ExhibitionStatus.END, "Picasso.jpg"),
                        tuple("Vertigo of Color: Matisse, Derain, and the Origins of Fauvism",
                                "The exhibition is made possible by The Florence Gould Foundation.", "MET",
                                LocalDate.parse("2023-10-13"), LocalDate.parse("2024-01-21"), ExhibitionStatus.END,
                                "Fauvism.jpg"),
                        tuple("Proof: Maxime Du Camp’s Photographs of the Eastern Mediterranean and North Africa",
                                "Officially encouraged to exploit photography’s “uncontestable exactitude,",
                                "MET", LocalDate.parse("2023-10-23"), LocalDate.parse("2024-01-21"),
                                ExhibitionStatus.END,
                                "Mediterranean.jpg"),
                        tuple("Christmas Tree and Neapolitan Baroque Crèche",
                                "The Met continues a longstanding holiday tradition with the presentation of its Christmas tree.",
                                "MET",
                                LocalDate.parse("2023-11-21"), LocalDate.parse("2024-01-07"), ExhibitionStatus.END,
                                "tree.jpg"));
    }
}