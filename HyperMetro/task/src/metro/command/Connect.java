package metro.command;

import metro.entity.LineStation;
import metro.entity.Metro;
import metro.entity.Station;
import metro.ui.UserInterface;

import java.util.List;
import java.util.NoSuchElementException;

public class Connect extends MetroCommand {
    private final UserInterface ui;

    public Connect(final Metro metro, final UserInterface ui) {
        super(metro);
        this.ui = ui;
    }

    @Override
    public void accept(final List<String> parameters) {
        try {
            verify(parameters);
            final var source = getStation(parameters.get(0), parameters.get(1));
            final var target = getStation(parameters.get(2), parameters.get(3));
            source.setTransfer(getTransfer(parameters.get(2), parameters.get(3)));
            target.setTransfer(getTransfer(parameters.get(0), parameters.get(1)));
        } catch (IllegalArgumentException | NoSuchElementException exception) {
            ui.printLine(exception.getMessage());
        }
    }

    private List<LineStation> getTransfer(final String line, final String station) {
        return List.of(new LineStation(line, station));
    }

    private void verify(final List<String> parameters) {
        if (parameters.size() != 4) {
            throw new IllegalArgumentException("Invalid number of parameters");
        }
    }

    private Station getStation(final String lineName, final String stationName) {
        return metro.getLine(lineName)
                .orElseThrow(() -> new NoSuchElementException("No metro line with name " + lineName))
                .getByName(stationName)
                .orElseThrow(() -> new NoSuchElementException("No station with name " + stationName));
    }
}
