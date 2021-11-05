package metro.entity;

import java.util.List;

public class Station extends NamedEntity {
    private List<String> previousStations;
    private List<String> nextStations;
    private List<LineStation> transfer;

    public Station(final JsonStation station,
                   final List<String> previousStations,
                   final List<String> nextStations) {
        super(station.getName());
        this.previousStations = previousStations;
        this.nextStations = nextStations;
        this.transfer = station.getTransfer();
    }

    public List<String> getPreviousStations() {
        return previousStations;
    }

    public void setPreviousStations(final List<String> previousStations) {
        this.previousStations = previousStations;
    }

    public List<String> getNextStations() {
        return nextStations;
    }

    public void setNextStations(final List<String> nextStations) {
        this.nextStations = nextStations;
    }
}
