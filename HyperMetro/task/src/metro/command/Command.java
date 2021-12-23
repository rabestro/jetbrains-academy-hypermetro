package metro.command;

import java.util.List;
import java.util.function.Function;

@FunctionalInterface
public interface Command extends Function<List<String>, String> {
}
