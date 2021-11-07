package metro.service;

import lombok.AllArgsConstructor;
import metro.command.Command;
import metro.ui.UserInterface;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@AllArgsConstructor
public class RequestParser {
    private static final Pattern COMMAND_PATTERN =
            Pattern.compile("/?(?<command>[-\\w]+)(?:\\s+(?<parameters>.*))?");

    private final UserInterface ui;
    private final ParameterParser parameterParser;
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
        final var parameters = parameterParser.parse(matcher.group("parameters"));

        return () -> {
            try {
                ui.printLine(actions.get(commandName).apply(parameters));
            } catch (NoSuchElementException | IllegalArgumentException exception) {
                ui.printLine(exception.getMessage());
            }
        };
    }
}
