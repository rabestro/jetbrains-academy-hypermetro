package metro.domain;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class MetroStation {
    private final StationID stationID;
    private final Set<StationID> transfer;
    private Set<StationID> next = new HashSet<>();
    private Set<StationID> prev = new HashSet<>();

    static MetroStation from(final String line, final JsonObject jsonStation) {
        final var name = jsonStation.get("name").getAsString();
        final var transfer = parseTransfer(jsonStation.get("transfer"));
        final var stationId = new StationID(line, name);
        return new MetroStation(stationId, transfer);
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
