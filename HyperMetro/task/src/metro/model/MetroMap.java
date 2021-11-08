package metro.model;

import lombok.Setter;

import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toUnmodifiableMap;

@Setter
public class MetroMap {
    private Map<String, MetroLine> lines = Map.of();

    public Optional<MetroLine> getLine(final String name) {
        return Optional.ofNullable(lines.get(name));
    }

    public Map<StationID, MetroNode> getNodes() {
        return lines.values().stream().flatMap(MetroLine::stream)
                .collect(toUnmodifiableMap(MetroStation::getStationID, MetroNode::new));
    }
}