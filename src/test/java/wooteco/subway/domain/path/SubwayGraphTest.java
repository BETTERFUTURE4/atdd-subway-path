package wooteco.subway.domain.path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static wooteco.subway.domain.fixtures.TestFixtures.강남;
import static wooteco.subway.domain.fixtures.TestFixtures.강남_삼성;
import static wooteco.subway.domain.fixtures.TestFixtures.건대;
import static wooteco.subway.domain.fixtures.TestFixtures.건대_성수;
import static wooteco.subway.domain.fixtures.TestFixtures.당고개;
import static wooteco.subway.domain.fixtures.TestFixtures.분당선;
import static wooteco.subway.domain.fixtures.TestFixtures.삼성;
import static wooteco.subway.domain.fixtures.TestFixtures.삼성_건대;
import static wooteco.subway.domain.fixtures.TestFixtures.성수;
import static wooteco.subway.domain.fixtures.TestFixtures.성수_강남;
import static wooteco.subway.domain.fixtures.TestFixtures.신촌;
import static wooteco.subway.domain.fixtures.TestFixtures.왕십리_합정;
import static wooteco.subway.domain.fixtures.TestFixtures.이호선;
import static wooteco.subway.domain.fixtures.TestFixtures.창동;
import static wooteco.subway.domain.fixtures.TestFixtures.창동_당고개;
import static wooteco.subway.domain.fixtures.TestFixtures.합정;
import static wooteco.subway.domain.fixtures.TestFixtures.합정_성수;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import wooteco.subway.domain.element.Section;
import wooteco.subway.domain.element.Station;
import wooteco.subway.domain.fare.Fare;
import wooteco.subway.domain.fare.PolicyFactory;

public class SubwayGraphTest {

    private static final List<Section> SECTIONS = List.of(강남_삼성, 삼성_건대, 건대_성수, 왕십리_합정, 합정_성수, 성수_강남, 창동_당고개);

    private static List<Arguments> getSections() {
        return List.of(
                Arguments.of(강남, 삼성, 1250),
                Arguments.of(삼성, 건대, 1350),
                Arguments.of(건대, 성수, 1450),
                Arguments.of(창동, 당고개, 2150));
    }

    @Test
    @DisplayName("두개 이상의 경로가 존재할 때 최단 경로를 반환한다.")
    void getShortestRoute() {
        SubwayGraph subwayGraph = new SubwayGraph(SECTIONS);

        List<Station> result = subwayGraph.getShortestRoute(강남, 합정);

        assertThat(result).containsExactly(강남, 성수, 합정);
    }

    @Test
    @DisplayName("두개 이상의 경로가 존재할 때 최단 거리를 반환한다.")
    void getShortestDistance() {
        SubwayGraph subwayGraph = new SubwayGraph(SECTIONS);

        int distance = subwayGraph.getShortestDistance(강남, 합정);

        assertThat(distance).isEqualTo(20);
    }

    @Test
    @DisplayName("거리가 존재하지 않는 경우 예외가 발생한다.")
    void notFoundRoute() {
        SubwayGraph subwayGraph = new SubwayGraph(SECTIONS);

        assertThatThrownBy(() -> subwayGraph.getShortestRoute(강남, 창동)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 경로가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("존재하지 않는 역으로 조회할 시 예외가 발생한다.")
    void notFoundStation() {
        SubwayGraph subwayGraph = new SubwayGraph(SECTIONS);

        assertThatThrownBy(() -> subwayGraph.getShortestRoute(강남, 신촌))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("graph must contain the sink vertex");
    }

    @Test
    @DisplayName("경로 상의 모든 노선을 반환한다.")
    void getLines() {
        SubwayGraph subwayGraph = new SubwayGraph(SECTIONS);
        assertThat(subwayGraph.getLines(강남, 건대)).containsOnly(이호선);
        assertThat(subwayGraph.getLines(강남, 합정)).containsOnly(이호선, 분당선);
    }

    @ParameterizedTest
    @MethodSource("getSections")
    @DisplayName("요금을 반환한다.")
    void getFare(Station source, Station target, int fare) {
        SubwayGraph subwayGraph = new SubwayGraph(SECTIONS);
        int distance = subwayGraph.getShortestDistance(source, target);
        int baseFare = PolicyFactory.createDistance(distance).getFare(distance);
        assertThat(new Fare(new ArrayList<>(), baseFare).getFare()).isEqualTo(fare);
    }
}
