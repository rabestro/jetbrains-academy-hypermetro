package metro.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Line extends NamedEntity implements Iterable<Station> {
    private static final List<String> DEPOT = Collections.emptyList();
    private final LinkedList<Station> stations = new LinkedList<>();

    public Line(final String lineName) {
        super(lineName);
    }

    @Override
    public Iterator<Station> iterator() {
        return stations.iterator();
    }

    public void append(final String stationName) {
        append(new JsonStation(stationName));
    }

    public void append(final JsonStation station) {
        final List<String> prevStations;

        if (stations.isEmpty()) {
            prevStations = DEPOT;
        } else {
            stations.getLast().setNextStations(List.of(station.getName()));
            prevStations = List.of(stations.getLast().name());
        }
        stations.add(new Station(station, prevStations, DEPOT));
    }

    public void addHead(final String stationName) {
        addHead(new JsonStation(stationName));
    }

    public void addHead(final JsonStation station) {
        final List<String> nextStations;
        if (stations.isEmpty()) {
            nextStations = DEPOT;
        } else {
            stations.getFirst().setPreviousStations(List.of(station.getName()));
            nextStations = List.of(stations.getFirst().name());
        }
        stations.addFirst(new Station(station, DEPOT, nextStations));
    }

    public void remove(final String stationName) {
        throw new UnsupportedOperationException("operation is not implemented");
    }
}
