package wooteco.subway.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static wooteco.subway.domain.fixtures.TestFixtures.강남;
import static wooteco.subway.domain.fixtures.TestFixtures.삼성;
import static wooteco.subway.domain.fixtures.TestFixtures.성수;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.subway.domain.element.Line;
import wooteco.subway.domain.element.Section;

public class SectionTest {

    private static final Line 이호선 = new Line(1L, "2호선", "green", 0);

    @Test
    @DisplayName("상행역을 기준으로 구간을 분리한다.")
    void splitByUpStation() {

        Section 기존_구간 = new Section(1L, 이호선, 삼성, 성수, 10);
        Section 추가할_구간 = new Section(이호선, 삼성, 강남, 4);

        List<Section> sections = 기존_구간.split(추가할_구간);
        assertThat(sections.get(0).getId()).isNotNull();
        assertThat(sections.get(0).getUpStation()).isEqualTo(삼성);
        assertThat(sections.get(0).getDownStation()).isEqualTo(강남);
        assertThat(sections.get(0).getDistance()).isEqualTo(4);

        assertThat(sections.get(1).getId()).isNull();
        assertThat(sections.get(1).getUpStation()).isEqualTo(강남);
        assertThat(sections.get(1).getDownStation()).isEqualTo(성수);
        assertThat(sections.get(1).getDistance()).isEqualTo(6);
    }

    @Test
    @DisplayName("하행역을 기준으로 구간을 분리한다.")
    void splitByDownStation() {
        Section 기존_구간 = new Section(1L, 이호선, 삼성, 성수, 10);
        Section 추가할_구간 = new Section(이호선, 강남, 성수, 4);

        List<Section> sections = 기존_구간.split(추가할_구간);
        assertThat(sections.get(0).getId()).isNotNull();
        assertThat(sections.get(0).getUpStation()).isEqualTo(삼성);
        assertThat(sections.get(0).getDownStation()).isEqualTo(강남);
        assertThat(sections.get(0).getDistance()).isEqualTo(6);

        assertThat(sections.get(1).getId()).isNull();
        assertThat(sections.get(1).getUpStation()).isEqualTo(강남);
        assertThat(sections.get(1).getDownStation()).isEqualTo(성수);
        assertThat(sections.get(1).getDistance()).isEqualTo(4);
    }

    @Test
    @DisplayName("분리할 구간이 없다면 종점 구간을 반환한다")
    void cannotSplitSectionReturnEndSection() {
        Section 기존_구간 = new Section(1L, 이호선, 삼성, 성수, 10);
        Section 추가할_구간 = new Section(이호선, 강남, 삼성, 4);

        List<Section> sections = 기존_구간.split(추가할_구간);
        assertThat(sections.get(0).getId()).isNull();
        assertThat(sections.get(0).getUpStation()).isEqualTo(강남);
        assertThat(sections.get(0).getDownStation()).isEqualTo(삼성);
        assertThat(sections.get(0).getDistance()).isEqualTo(4);
    }

    @Test
    @DisplayName("구간에 상행역과 하행역중 하나라도 일치하는지 확인한다.")
    void isEqualToUpOrDownStation() {
        Section 기존_구간 = new Section(1L, 이호선, 삼성, 성수, 10);

        assertThat(기존_구간.isEqualToUpOrDownStation(삼성)).isTrue();
        assertThat(기존_구간.isEqualToUpOrDownStation(성수)).isTrue();
    }

    @Test
    @DisplayName("구간의 상행역이 같은지 확인한다.")
    void isEqualToUpStation() {
        Section 기존_구간 = new Section(1L, 이호선, 삼성, 성수, 10);

        assertThat(기존_구간.isEqualToUpStation(삼성)).isTrue();
    }

    @Test
    @DisplayName("구간의 하행역이 같은지 확인한다.")
    void isEqualToDownStation() {
        Section 기존_구간 = new Section(1L, 이호선, 삼성, 성수, 10);

        assertThat(기존_구간.isEqualToDownStation(성수)).isTrue();
    }

    @Test
    @DisplayName("구간의 라인이 같은지 확인한다.")
    void isEqualToLine() {
        Section 첫번째_구간 = new Section(1L, 이호선, 삼성, 성수, 10);

        assertThat(첫번째_구간.isEqualToLine(이호선)).isTrue();
    }

    @Test
    @DisplayName("기존 구간보다 같거나 큰 경우 예외를 발생한다.")
    void throwExceptionIsLongDistance() {
        Section 기존_구간 = new Section(1L, 이호선, 삼성, 성수, 10);
        Section 추가할_구간 = new Section(이호선, 강남, 성수, 10);

        assertThatThrownBy(() -> 기존_구간.split(추가할_구간))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("추가하려는 거리가 큽니다.");
    }

    @Test
    @DisplayName("구간 생성시 거리가 1보다 작은 경우 예외를 발생한다.")
    void invalidDistance() {
        assertThatThrownBy(() -> new Section(1L, 이호선, 삼성, 성수, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("거리는 1이상이어야 합니다.");
    }

    @Test
    @DisplayName("첫번째구간의 하행역과 두번째구간의 상행역이 같으면 연결이 가능하다.")
    void connect() {
        Section 첫번째_구간 = new Section(1L, 이호선, 삼성, 성수, 10);
        Section 두번째_구간 = new Section(이호선, 성수, 강남, 11);

        assertThat(첫번째_구간.canConnectToNext(두번째_구간)).isTrue();
    }
}
