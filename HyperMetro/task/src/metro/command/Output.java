package metro.command;

import metro.entity.Metro;

import java.util.List;

public class Output extends MetroCommand {
    Output(final Metro metro) {
        super(metro);
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
