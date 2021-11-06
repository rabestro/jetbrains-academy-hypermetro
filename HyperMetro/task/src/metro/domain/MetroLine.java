package metro.domain;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MetroLine {
    private final String name;
    private final LinkedHashMap<String, MetroStation> stations;

    static MetroLine from(final Map.Entry<String, JsonElement> jsonLine) {
        final var jsonStations = jsonLine.getValue().getAsJsonObject();
        final var stations = new LinkedHashMap<String, MetroStation>();
        final var metroLineName = jsonLine.getKey();

        jsonStations.entrySet().forEach(station -> {
            final var jsonStation = station.getValue().getAsJsonObject();
            final var metroStation = MetroStation.from(metroLineName, jsonStation);
            stations.put(metroStation.getStationID().getName(), metroStation);
        });

        return new MetroLine(metroLineName, stations);
    }

}
