package metro.entity;

public class LineStation {
    private final String line;
    private final String station;

    public LineStation(final String line, final String station) {
        this.line = line;
        this.station = station;
    }

    public String getLine() {
        return line;
    }

    public String getStation() {
        return station;
    }
}
