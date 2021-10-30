package metro.command;

import metro.entity.Metro;

import java.util.List;
import java.util.function.Predicate;

abstract class MetroCommand implements Command {
    protected final Metro metro;

    MetroCommand(final Metro metro) {
        this.metro = metro;
    }

}
