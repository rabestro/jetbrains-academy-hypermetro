package metro.command;

import metro.entity.Metro;
import metro.ui.UserInterface;

import java.util.List;

public class AddHead extends MetroCommand {
    private final UserInterface ui;

    public AddHead(final Metro metro, final UserInterface ui) {
        super(metro);
        this.ui = ui;
    }

    @Override
    public void accept(final List<String> parameters) {
        if (parameters.size() != 2) {
            ui.printLine("Invalid number of parameters");
            return;
        }
        final var lineName = parameters.get(0);
        final var stationName = parameters.get(1);

        metro.getLine(lineName).ifPresentOrElse(
                line -> line.addHead(stationName),
                () -> ui.printLine("The line '" + lineName + "' is invalid.")
        );
    }
}
