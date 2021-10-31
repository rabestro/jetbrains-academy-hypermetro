package metro.entity;

import java.util.List;

public class Station extends NamedEntity {
    private List<String> previousStations;
    private List<String> nextStations;

    public Station(final String stationName,
                   final List<String> previousStations,
                   final List<String> nextStations) {
        super(stationName);
        this.previousStations = previousStations;
        this.nextStations = nextStations;
    }

    public void setNextStations(final List<String> nextStations) {
        this.nextStations = nextStations;
    }

    public void setPreviousStations(final List<String> previousStations) {
        this.previousStations = previousStations;
    }

    public List<String> getPreviousStations() {
        return previousStations;
    }

    public List<String> getNextStations() {
        return nextStations;
    }
}
