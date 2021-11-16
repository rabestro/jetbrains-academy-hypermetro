package metro.repository;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import metro.model.MetroLine;
import metro.model.MetroMap;
import metro.model.MetroStation;
import metro.model.StationID;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.System.Logger.Level.*;
import static java.lang.System.Logger.Level.TRACE;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

class MapLoader {
    private static final System.Logger LOGGER = System.getLogger("MapLoader");

    public MetroMap load(final String fileName) throws IOException {
        LOGGER.log(INFO, "Loading Metro from file: " + fileName);
        final var reader = Files.newBufferedReader(Paths.get(fileName));
        final var json = new JsonParser().parse(reader);
        final var lines = json.getAsJsonObject()
                .entrySet().stream()
                .map(this::parseMetroLine)
                .collect(toUnmodifiableMap(MetroLine::getLineName, identity()));
        LOGGER.log(INFO, "Loaded metro lines: " + lines.keySet());
        return new MetroMap(lines);
    }

    private static int getTime(final JsonObject jsonStation) {
        final var hasTime = jsonStation.has("time") && !jsonStation.get("time").isJsonNull();
        return hasTime ? jsonStation.get("time").getAsInt() : 1;
    }

    private static Set<StationID> parseStations(final String line, final JsonElement jsonElement) {
        LOGGER.log(TRACE, "Parse stations {0}", jsonElement);
        final var stations = new HashSet<StationID>();
        if (!jsonElement.isJsonNull()) {
            jsonElement.getAsJsonArray().forEach(element -> {
                final var name = element.getAsString();
                final var stationId = new StationID(line, name);
                stations.add(stationId);
            });
        }
        LOGGER.log(TRACE, "Stations: {0}", stations);
        return stations;
    }

    private MetroLine parseMetroLine(final Map.Entry<String, JsonElement> jsonLine) {
        final var lineName = jsonLine.getKey();
        final var metroLine = new MetroLine(lineName);
        LOGGER.log(DEBUG, "Import metro line: " + lineName);

        final var jsonStations = jsonLine.getValue().getAsJsonArray();
        jsonStations.forEach(station -> {
            final var jsonStation = station.getAsJsonObject();
            final var metroStation = parseMetroStation(lineName, jsonStation);
            metroLine.add(metroStation);
        });
        return metroLine;
    }

    private MetroStation parseMetroStation(final String line, final JsonObject jsonStation) {
        final var name = jsonStation.get("name").getAsString();
        final var station = new MetroStation(new StationID(line, name));
        LOGGER.log(TRACE, "Import station '" + name + "' (" + line + ")");
        station.setTime(getTime(jsonStation));
        station.setPrev(parseStations(line, jsonStation.get("prev")));
        station.setNext(parseStations(line, jsonStation.get("next")));
        station.setTransfer(parseTransfer(jsonStation.get("transfer")));
        LOGGER.log(TRACE, station);
        return station;
    }

    private Set<StationID> parseTransfer(final JsonElement jsonElement) {
        final var transfer = new HashSet<StationID>();
        if (!jsonElement.isJsonNull()) {
            jsonElement.getAsJsonArray().forEach(element -> {
                final var jsonObject = element.getAsJsonObject();
                final var stationId = parseStationId(jsonObject);
                transfer.add(stationId);
            });
        }
        return transfer;
    }

    private StationID parseStationId(final JsonObject jsonObject) {
        return new StationID(
                jsonObject.get("line").getAsString(),
                jsonObject.get("station").getAsString()
        );
    }

}
