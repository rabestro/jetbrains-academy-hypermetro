package metro.model;

import lombok.Value;

@Value
public class PathStep {
    MetroStation station;
    PathStep previous;
}
