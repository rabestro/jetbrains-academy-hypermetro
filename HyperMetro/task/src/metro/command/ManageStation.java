package metro.command;

import metro.entity.Line;
import metro.entity.Metro;
import metro.ui.UserInterface;

import java.util.List;
import java.util.function.BiConsumer;

public class ManageStation extends MetroCommand {
    private final UserInterface ui;
    private final BiConsumer<Line, String> action;
    private String lineName;
    private String stationName;

    public ManageStation(
            final Metro metro,
            final UserInterface ui,
            final BiConsumer<Line, String> action) {
        super(metro);
        this.ui = ui;
        this.action = action;
    }

    @Override
    public void accept(final List<String> parameters) {
        if (parameters.size() != 2) {
            ui.printLine("Invalid number of parameters");
            return;
        }
        lineName = parameters.get(0);
        stationName = parameters.get(1);

        metro.getLine(lineName).ifPresentOrElse(
                line -> action.accept(line, stationName),
                () -> ui.printLine("The line '" + lineName + "' is invalid.")
        );
    }
}
