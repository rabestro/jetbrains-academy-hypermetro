package metro.command;

import metro.entity.Metro;
import metro.ui.UserInterface;

import java.util.List;

public class Output extends MetroCommand {
    protected final UserInterface ui;

    public Output(final Metro metro, final UserInterface ui) {
        super(metro);
        this.ui = ui;
    }

    @Override
    public boolean execute(final List<String> parameters) {
        if (parameters.size() != 1) {
            return false;
        }
        final var line = metro.getLine(parameters.get(0));

        if (line.isEmpty()) {
            return false;
        }


        return true;
    }
}
