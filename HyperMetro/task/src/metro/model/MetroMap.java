package metro.model;

import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toUnmodifiableMap;

public class MetroMap {
    private Map<String, MetroLine> lines = Map.of();

    public void load(final String fileName) throws IOException {
        final var reader = Files.newBufferedReader(Paths.get(fileName));
        final var json = new JsonParser().parse(reader);
        lines = json.getAsJsonObject().entrySet().stream().map(MetroLine::from)
                .collect(toUnmodifiableMap(MetroLine::getName, Function.identity()));
    }

    public Optional<MetroLine> getLine(final String name) {
        return Optional.ofNullable(lines.get(name));
    }
}
