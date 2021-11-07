package metro.model;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MetroLine implements Iterable<MetroStation> {
    private final String lineName;
    private final LinkedList<MetroStation> stations;

    public static MetroLine from(final Map.Entry<String, JsonElement> jsonLine) {
        final var jsonStations = jsonLine.getValue().getAsJsonObject();
        final var stations = new LinkedList<MetroStation>();
        final var metroLineName = jsonLine.getKey();

        jsonStations.entrySet().forEach(station -> {
            final var jsonStation = station.getValue().getAsJsonObject();
            final var metroStation = MetroStation.from(metroLineName, jsonStation);
            stations.add(metroStation);
        });

        return new MetroLine(metroLineName, stations);
    }

    public Optional<MetroStation> getStation(final String name) {
        return stations.stream().filter(s -> s.getStationID().getName().equals(name)).findAny();
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
        final var station = new MetroStation(sid);
        if (!stations.isEmpty()) {
            final var lastStation = stations.getLast();
            lastStation.setNext(Set.of(sid));
            station.setPrev(Set.of(lastStation.getStationID()));
        }
        stations.add(station);
    }

    @Override
    public Iterator<MetroStation> iterator() {
        return stations.iterator();
    }
}
