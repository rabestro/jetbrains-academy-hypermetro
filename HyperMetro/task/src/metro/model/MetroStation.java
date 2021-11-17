package metro.model;

import lombok.Data;

import java.util.Set;

@Data
public class MetroStation {
    private final StationID stationID;
    private final int time;
    private Set<StationID> transfer = Set.of();
    private Set<StationID> next = Set.of();
    private Set<StationID> prev = Set.of();
}
