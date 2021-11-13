package metro.model;

import lombok.Setter;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableMap;

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

    public Map<StationID, MetroNode> getNodes() {
        return lines.values().stream().flatMap(MetroLine::stream)
                .collect(toUnmodifiableMap(MetroStation::getStationID, MetroNode::new));
    }

    public Stream<StationID> stream() {
        return lines.values().stream().flatMap(MetroLine::stream).map(MetroStation::getStationID);
    }

    public Set<MetroStation> getAllStations() {
        return lines.values().stream()
                .flatMap(MetroLine::stream)
                .collect(Collectors.toUnmodifiableSet());
    }

}