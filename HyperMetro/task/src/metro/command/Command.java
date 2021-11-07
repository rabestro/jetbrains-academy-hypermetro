package metro.command;

import java.util.List;
import java.util.function.Consumer;

public interface Command extends Consumer<List<String>> {
    String name();
}
