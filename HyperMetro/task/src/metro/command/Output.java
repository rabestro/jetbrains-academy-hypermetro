package metro.command;

import metro.entity.Line;
import metro.entity.Metro;
import metro.entity.Station;
import metro.ui.UserInterface;

import java.util.List;

public class Output extends MetroCommand {
    private static final String DEPOT = "depot";
    protected final UserInterface ui;

    public Output(final Metro metro, final UserInterface ui) {
        super(metro);
        this.ui = ui;
    }

    @Override
    public void accept(final List<String> parameters) {
        if (parameters.size() != 1) {
            ui.printLine("Invalid number of parameters");
            return;
        }
        final var lineName = parameters.get(0);
        metro.getLine(lineName).ifPresentOrElse(
                this::printLine,
                () -> ui.printLine("The line " + lineName + " is invalid.")
        );
    }

    private void printLine(final Line line) {
        line.forEach(this::printStation);
    }

    private void printStation(final Station station) {
        final var prev = station.getPreviousStations().isEmpty() ? DEPOT
                : station.getPreviousStations().get(0);
        final var next = station.getNextStations().isEmpty() ? DEPOT
                : station.getNextStations().get(0);
        ui.printLine(prev + " - " + station.name() + " - " + next);
    }
}
