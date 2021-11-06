package metro.entity;

import lombok.Data;

@Data
public class StationID {
    private final String line;
    private final String name;
}
