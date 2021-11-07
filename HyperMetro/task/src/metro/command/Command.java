package metro.command;

import java.util.List;
import java.util.function.Function;

public interface Command extends Function<List<String>, String> {
    String name();
}
