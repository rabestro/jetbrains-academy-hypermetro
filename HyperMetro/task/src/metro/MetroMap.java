package metro;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MetroMap {
    private static final String DEPOT = "depot";

    private final LinkedList<String> metroLine;

    public MetroMap(final List<String> metroMap) {
        this.metroLine = new LinkedList<>(metroMap);
        if (!metroLine.isEmpty()) {
            metroLine.addFirst(DEPOT);
            metroLine.addLast(DEPOT);
        }
    }

    List<String> getLine() {
        return Collections.unmodifiableList(metroLine);
    }
}
