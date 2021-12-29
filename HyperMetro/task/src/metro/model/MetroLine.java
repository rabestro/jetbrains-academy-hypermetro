package metro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
@ToString
@AllArgsConstructor
public class MetroLine implements Iterable<Station> {
    private static final System.Logger LOGGER = System.getLogger("HyperMetro");

    private final String lineName;
    private final LinkedList<Station> stations = new LinkedList<>();

    public Optional<Station> getStation(final String name) {
        return stations.stream().filter(s -> s.id().name().equals(name)).findAny();
    }

    public void remove(final Station station) {
        stations.remove(station);
    }

    public void addHead(final String name) {
        final var sid = new StationId(lineName, name);
        final var station = new Station(sid);
        if (!stations.isEmpty()) {
            final var prevStation = stations.getFirst();
            prevStation.prev().add(sid);
            station.next().add(prevStation.id());
        }
        stations.addFirst(station);
    }

    public void add(final Station metroStation) {
        stations.add(metroStation);
    }

    public void append(final String stationName) {
        final var sid = new StationId(lineName, stationName);
        append(new Station(sid));
    }

    private void append(final Station metroStation) {
        if (!stations.isEmpty()) {
            final var lastStation = stations.getLast();
            lastStation.next().add(metroStation.id());
            metroStation.prev().add(lastStation.id());
        }
        stations.add(metroStation);
    }

    @Override
    public Iterator<Station> iterator() {
        return stations.iterator();
    }

    public Stream<Station> stream() {
        return stations.stream();
    }
}
