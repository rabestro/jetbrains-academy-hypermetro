package metro.entity;

import com.google.gson.JsonObject;
import lombok.Data;

@Data
public class MetroStation {
    private final String name;

    static MetroStation from(final JsonObject jsonStation) {
        final var name = jsonStation.get("name").getAsString();
        return new MetroStation(name);
    }
}
