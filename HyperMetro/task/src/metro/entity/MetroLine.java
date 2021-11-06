package metro.entity;

import com.google.gson.JsonElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MetroLine {
    private final String name;
    private final LinkedList<MetroStation> stations;

    static MetroLine from(final Map.Entry<String, JsonElement> jsonLine) {
        final var stations = new LinkedList<MetroStation>();

        return new MetroLine(jsonLine.getKey(), stations);
    }
}
