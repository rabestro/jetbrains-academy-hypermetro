package metro.entity;

import java.util.LinkedList;

public class Line extends NamedEntity {
    private final LinkedList<Station> stations = new LinkedList<>();

    public Line(final String lineName) {
        super(lineName);
    }

    public void append(final String stationName) {
        stations.add(new Station(stationName));
    }

    public void addHead(final String stationName) {
        stations.addFirst(new Station(stationName));
    }
}
