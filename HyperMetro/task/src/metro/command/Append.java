package metro.command;

import metro.entity.Metro;

import java.util.List;

public class Append extends MetroCommand {
    public Append(final Metro metro) {
        super(metro);
    }

    public boolean execute(final List<String> args) {
        if (args.size() != 2) {
            return false;
        }

        return false;
    }
}
