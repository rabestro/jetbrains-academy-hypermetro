package metro.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import metro.entity.Line;
import metro.entity.Metro;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;

public class MetroLoader {
    public Metro load(final String fileName) throws IOException {
        final var path = Paths.get(fileName);
        if (Files.notExists(path)) {
            throw new IllegalArgumentException("Error! Such a file doesn't exist!");
        }
        final var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        final var gson = new Gson();

        Map<String, SortedMap<Integer, String>> schema = gson.fromJson(reader,
                new TypeToken<Map<String, SortedMap<Integer, String>>>() {
                }.getType());

        return createMetro(schema);
    }

    Metro createMetro(Map<String, SortedMap<Integer, String>> schema) {
        final var lines = schema.entrySet().stream()
                .map(this::createLine)
                .collect(Collectors.toUnmodifiableSet());
        return new Metro(lines);
    }

    Line createLine(Map.Entry<String, SortedMap<Integer, String>> lineEntry) {
        final var metroLine = new Line(lineEntry.getKey());
        lineEntry.getValue().values().forEach(metroLine::append);
        return metroLine;
    }
}
