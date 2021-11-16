package metro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Getter
@ToString
@AllArgsConstructor
public class MetroLine implements Iterable<MetroStation> {
    private static final System.Logger LOGGER = System.getLogger("MetroLine");

    private final String lineName;
    private final LinkedList<MetroStation> stations = new LinkedList<>();

    public Optional<MetroStation> getStation(final String name) {
        return stations.stream().filter(s -> s.getStationID().getName().equals(name)).findAny();
    }

    public void remove(final MetroStation station) {
        stations.remove(station);
    }

    public void addHead(final String name) {
        final var sid = new StationID(lineName, name);
        final var station = new MetroStation(sid);
        if (!stations.isEmpty()) {
            final var prevStation = stations.getFirst();
            prevStation.setPrev(Set.of(sid));
            station.setNext(Set.of(prevStation.getStationID()));
        }
        stations.addFirst(station);
    }

    public void add(final MetroStation metroStation) {
        stations.add(metroStation);
    }

    public void append(final String name) {
        final var sid = new StationID(lineName, name);
        append(new MetroStation(sid));
    }

    public void append(final MetroStation metroStation) {
        if (!stations.isEmpty()) {
            final var lastStation = stations.getLast();
            lastStation.setNext(Set.of(metroStation.getStationID()));
            metroStation.setPrev(Set.of(lastStation.getStationID()));
        }
        stations.add(metroStation);
    }

    @Override
    public Iterator<MetroStation> iterator() {
        return stations.iterator();
    }

    public Stream<MetroStation> stream() {
        return stations.stream();
    }
}
