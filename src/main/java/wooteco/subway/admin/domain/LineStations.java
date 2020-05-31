package wooteco.subway.admin.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.jgrapht.graph.WeightedMultigraph;

import wooteco.subway.admin.exception.NonExistentDataException;

/**
 *   class description
 *
 *   @author ParkDooWon
 */
public class LineStations {
	private Set<LineStation> stations = new HashSet<>();

	public void addLineStation(LineStation lineStation) {
		stations.stream()
			.filter(lineStation1 -> LineStation.isSameId(lineStation1.getPreStationId(), lineStation.getPreStationId()))
			.findAny()
			.ifPresent(lineStation1 -> lineStation1.updatePreLineStation(lineStation.getStationId()));

		stations.add(lineStation);
	}

	public void removeLineStationById(Long stationId) {
		LineStation targetLineStation = stations.stream()
			.filter(lineStation -> LineStation.isSameId(lineStation.getStationId(), stationId))
			.findFirst()
			.orElseThrow(() -> new NonExistentDataException("존재하지 않는 LineStation입니다."));

		stations.stream()
			.filter(lineStation -> LineStation.isSameId(lineStation.getPreStationId(), stationId))
			.findFirst()
			.ifPresent(lineStation -> lineStation.updatePreLineStation(targetLineStation.getPreStationId()));

		stations.remove(targetLineStation);
	}

	public List<Long> getLineStationsId() {
		if (stations.isEmpty()) {
			return new ArrayList<>();
		}

		List<Long> stationIds = new ArrayList<>();
		Map<Long, Long> linkedStationIds = new HashMap<>();
		for (LineStation lineStation : stations) {
			linkedStationIds.put(lineStation.getPreStationId(), lineStation.getStationId());
		}

		Long firstLineStationId = linkedStationIds.get(null);
		stationIds.add(firstLineStationId);
		while (stationIds.size() != linkedStationIds.size()) {
			Long lastStationId = stationIds.get(stationIds.size() - 1);
			stationIds.add(linkedStationIds.get(lastStationId));
		}

		return stationIds;
	}

	public void setUpGraph(WeightedMultigraph<Long, CustomEdge> graph, EdgeWeightType type) {
		stations.forEach(lineStation -> setUpGraph(graph, lineStation, type));
	}

	private void setUpGraph(WeightedMultigraph<Long, CustomEdge> graph, LineStation lineStation, EdgeWeightType type) {
		Long preStationId = lineStation.getPreStationId();
		Long stationId = lineStation.getStationId();

		if (Objects.nonNull(preStationId)) {
			CustomEdge customEdge = new CustomEdge(lineStation, type);
			graph.addEdge(preStationId, stationId, customEdge);
			graph.setEdgeWeight(customEdge, customEdge.getWeight());
		}
	}

	public Set<LineStation> getStations() {
		return stations;
	}
}