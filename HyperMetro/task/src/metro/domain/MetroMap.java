package metro.domain;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toUnmodifiableMap;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MetroMap {
    private final Map<String, MetroLine> lines;

    public static MetroMap from(final String fileName) throws IOException {
        final var reader = Files.newBufferedReader(Paths.get(fileName));
        final var json = new JsonParser().parse(reader);
        return from(json.getAsJsonObject());
    }

    private static MetroMap from(final JsonObject json) {
        final var lines = json.entrySet().stream()
                .map(MetroLine::from)
                .collect(toUnmodifiableMap(MetroLine::getName, Function.identity()));
        return new MetroMap(lines);
    }

    public Optional<MetroLine> getLine(final String name) {
        return Optional.ofNullable(lines.get(name));
    }
}
