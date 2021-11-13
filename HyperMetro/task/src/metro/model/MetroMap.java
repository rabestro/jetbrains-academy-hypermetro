package metro.model;

import lombok.Setter;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Setter
public class MetroMap {
    private Map<String, MetroLine> lines = Map.of();

    public Optional<MetroLine> getLine(final String name) {
        return Optional.ofNullable(lines.get(name));
    }

    public Optional<MetroStation> getStation(final StationID stationID) {
        return getLine(stationID.getLine())
                .flatMap(metroLine -> metroLine.getStation(stationID.getName()));
    }

    public Stream<StationID> stream() {
        return lines.values().stream().flatMap(MetroLine::stream).map(MetroStation::getStationID);
    }

}