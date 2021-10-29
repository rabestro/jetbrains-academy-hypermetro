package metro;

import java.util.List;

@SuppressWarnings("squid:S106")
public class Printer {
    private static final String DELIMITER = " - ";

    void printMetroLine(final List<String> metroLine) {
        if (metroLine.isEmpty()) {
            return;
        }
        for (var i = 1; i < metroLine.size() - 1; i++) {
            final var left = metroLine.get(i - 1);
            final var station = metroLine.get(i);
            final var right = metroLine.get(i + 1);
            System.out.println(left + DELIMITER + station + DELIMITER + right);
        }
    }

}
