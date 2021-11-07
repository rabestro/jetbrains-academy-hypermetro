package metro.model;

import lombok.Setter;

import java.util.Map;
import java.util.Optional;

@Setter
public class MetroMap {
    private Map<String, MetroLine> lines = Map.of();

    public Optional<MetroLine> getLine(final String name) {
        return Optional.ofNullable(lines.get(name));
    }
}
