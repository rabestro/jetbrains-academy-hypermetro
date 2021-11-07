package metro.model;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MetroLine implements Iterable<MetroStation> {
    private final String name;
    private final LinkedList<MetroStation> stations;

    public void append(final String name) {
        final var station = new MetroStation(new StationID(this.name, name), Set.of());
        if (!stations.isEmpty()) {
            station.setPrev(Set.of(stations.getLast().getStationID()));
        }
        stations.add(station);
    }

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

    @Override
    public Iterator<MetroStation> iterator() {
        return stations.iterator();
    }
}
