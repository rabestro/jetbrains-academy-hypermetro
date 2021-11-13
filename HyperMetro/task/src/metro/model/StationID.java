package metro.model;

import java.util.Objects;

public class StationID {
    private final String line;
    private final String name;

    public StationID(final String line, final String name) {
        this.line = line;
        this.name = name;
    }

    public String getLine() {
        return line;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var stationID = (StationID) o;
        return line.equals(stationID.line) && name.equals(stationID.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, name);
    }
}
