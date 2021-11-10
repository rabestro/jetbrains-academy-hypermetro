package metro.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import static java.lang.System.Logger.Level.DEBUG;

@Data
public class MetroStation {
    private static final System.Logger LOGGER = System.getLogger("MetroStation");

    private final StationID stationID;
    private Set<StationID> transfer = Set.of();
    private Set<StationID> next = Set.of();
    private Set<StationID> prev = Set.of();
    private int time;

    static MetroStation from(final String line, final JsonObject jsonStation) {
        final var name = jsonStation.get("name").getAsString();
        final var station = new MetroStation(new StationID(line, name));
        LOGGER.log(DEBUG, "Import station '" + name + "' (" + line + ")");

        station.setTime(getTime(jsonStation));
        station.setPrev(parseStations(line, jsonStation.get("prev")));
        station.setNext(parseStations(line, jsonStation.get("next")));
        station.setTransfer(parseTransfer(jsonStation.get("transfer")));
        return station;
    }

    private static int getTime(final JsonObject jsonStation) {
        final var hasTime = jsonStation.has("time") && !jsonStation.get("time").isJsonNull();
        return hasTime ? jsonStation.get("time").getAsInt() : 1;
    }

    private static Set<StationID> parseStations(final String line, final JsonElement jsonElement) {
        final var stations = new HashSet<StationID>();
        if (!jsonElement.isJsonNull()) {
            jsonElement.getAsJsonArray()
                    .forEach(element -> stations.add(new StationID(line, element.getAsString())));
        }
        return stations;
    }

    private static Set<StationID> parseTransfer(final JsonElement jsonElement) {
        final var transfer = new HashSet<StationID>();
        if (!jsonElement.isJsonNull()) {
            jsonElement.getAsJsonArray().forEach(element -> {
                final var jsonObject = element.getAsJsonObject();
                final var stationId = StationID.from(jsonObject);
                transfer.add(stationId);
            });
        }
        return transfer;
    }

}
