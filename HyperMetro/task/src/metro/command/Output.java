package metro.command;

import metro.entity.Line;
import metro.entity.LineStation;
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
        ui.printLine(DEPOT);
        line.forEach(this::printStation);
        ui.printLine(DEPOT);
    }

    private void printStation(final Station station) {
        ui.printLine(station.name() + printTransfer(station.getTransfer()));
    }

    private String printTransfer(List<LineStation> stations) {
        if (stations.isEmpty()) {
            return "";
        }
        final var first = stations.get(0);
        return " - " + first.getStation() + " (" + first.getLine() + " line)";
    }
}
