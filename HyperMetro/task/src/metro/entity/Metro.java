package metro.entity;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Metro {
    private final Map<String, Line> lines;

    public Metro(final Set<Line> lines) {
        this.lines = lines.stream()
                .collect(Collectors.toUnmodifiableMap(NamedEntity::name, Function.identity()));
    }

    public Optional<Line> getLine(final String name) {
        return Optional.ofNullable(lines.get(name));
    }
}
