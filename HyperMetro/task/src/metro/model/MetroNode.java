package metro.model;

import lombok.Data;

@Data
public class MetroNode {
    private final MetroStation station;
    private MetroNode previous;
    private int distance = Integer.MAX_VALUE;
    private boolean isVisited = false;

    public boolean notVisited() {
        return distance == Integer.MAX_VALUE;
    }

    public String getLine() {
        return station.getStationID().getLine();
    }

    public String getName() {
        return station.getStationID().getName();
    }

    public void visit() {
        isVisited = true;
    }

    public boolean isVisited() {
        return isVisited;
    }
}
