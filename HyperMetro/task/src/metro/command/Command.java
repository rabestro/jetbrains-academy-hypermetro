package metro.command;

import java.util.List;

public interface Command {
    boolean execute(List<String> parameters);
}
