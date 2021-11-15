package metro.model;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Stream;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MetroLine implements Iterable<MetroStation> {
    private final String lineName;
    private final LinkedList<MetroStation> stations = new LinkedList<>();

    public static MetroLine from(final Map.Entry<String, JsonElement> jsonLine) {
        final var jsonStations = jsonLine.getValue().getAsJsonObject();
        final var lineName = jsonLine.getKey();
        final var metroLine = new MetroLine(lineName);

        jsonStations.entrySet().forEach(station -> {
            final var jsonStation = station.getValue().getAsJsonObject();
            final var metroStation = MetroStation.from(lineName, jsonStation);
            metroLine.append(metroStation);
        });

        return metroLine;
    }

    Optional<MetroStation> getStation(final String name) {
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

    public void append(final String name) {
        final var sid = new StationID(lineName, name);
        append(new MetroStation(sid));
    }

    private void append(final MetroStation metroStation) {
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
