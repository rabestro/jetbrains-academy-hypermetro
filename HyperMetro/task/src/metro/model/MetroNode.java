package metro.model;

import lombok.Data;

@Data
public class MetroNode {
    private final MetroStation station;
    private MetroNode previous;
    private int distance;
}
