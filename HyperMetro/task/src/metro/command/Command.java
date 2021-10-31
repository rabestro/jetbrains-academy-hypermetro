package metro.command;

import java.util.List;

public interface Command {
    String name();

    boolean execute(List<String> parameters);
}
