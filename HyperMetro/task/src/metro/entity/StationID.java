package metro.entity;

import com.google.gson.JsonObject;
import lombok.Value;

@Value
public class StationID {
    String line;
    String name;

    static StationID from(final JsonObject jsonObject) {
        return new StationID(
                jsonObject.get("line").getAsString(),
                jsonObject.get("station").getAsString()
        );
    }
}
