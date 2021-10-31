package metro.entity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Line extends NamedEntity {
    private static final List<String> DEPOT = Collections.emptyList();
    private final LinkedList<Station> stations = new LinkedList<>();

    public Line(final String lineName) {
        super(lineName);
    }

    public void append(final String stationName) {
        final List<String> prevStations;

        if (stations.isEmpty()) {
            prevStations = DEPOT;
        } else {
            stations.getLast().setNextStations(List.of(stationName));
            prevStations = List.of(stations.getLast().name());
        }
        stations.add(new Station(stationName, prevStations, DEPOT));
    }

    public void addHead(final String stationName) {
        final List<String> nextStations;
        if (stations.isEmpty()) {
            nextStations = DEPOT;
        } else {
            stations.getFirst().setPreviousStations(List.of(stationName));
            nextStations = List.of(stations.getLast().name());
        }
        stations.addFirst(new Station(stationName, DEPOT, nextStations));
    }
}
