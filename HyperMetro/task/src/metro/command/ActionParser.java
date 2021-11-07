package metro.command;

import lombok.AllArgsConstructor;
import metro.model.MetroMap;
import metro.ui.UserInterface;

import java.util.Map;
import java.util.regex.Pattern;

@AllArgsConstructor
public class ActionParser {
    private static final Pattern COMMAND_PATTERN =
            Pattern.compile("/(?<command>[-\\w]+)(\\s+(?<parameters>.*))?");

    private final UserInterface ui;
    private final MetroMap metroMap;
    private final Map<String, Command> actions;
    private final Runnable defaultAction;

    public Runnable parse(final String userInput) {
        final var matcher = COMMAND_PATTERN.matcher(userInput);

        if (!matcher.matches()) {
            return defaultAction;
        }

        final var commandName = matcher.group("command").toLowerCase();

        if (!actions.containsKey(commandName)) {
            return defaultAction;
        }

        final var parameters = ParameterParser.parse(matcher.group("parameters"));

        return () -> {
            try {
                actions.get(commandName).accept(metroMap, parameters);
            } catch (IllegalArgumentException exception) {
                ui.printLine(exception.getMessage());
            }
        };
    }
}
