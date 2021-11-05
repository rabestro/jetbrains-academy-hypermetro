package metro.services;

import metro.command.Command;

import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RequestParser {
    private static final Pattern COMMAND = Pattern.compile("/(?<command>[-\\w]+)(\\s+(?<args>.*))?");
    private static final Pattern PARAMS = Pattern.compile("(\\w+)|\"([^\"]+)\"");
    private final Map<String, Command> commands;
    private final Runnable defaultCommand;

    public RequestParser(final Map<String, Command> commands, final Runnable defaultCommand) {
        this.commands = commands;
        this.defaultCommand = defaultCommand;
    }

    private static String extractParameter(final MatchResult result) {
        return result.group(2) == null ? result.group(1) : result.group(2);
    }

    public Runnable parse(final String userInput) {
        final var matcher = COMMAND.matcher(userInput);

        if (!matcher.matches()) {
            return defaultCommand;
        }
        final var command = matcher.group("command").toLowerCase();

        if (!commands.containsKey(command)) {
            return defaultCommand;
        }

        final var args = matcher.group("args");
        final var parameters = args == null ? List.<String>of() : PARAMS
                .matcher(args).results()
                .map(RequestParser::extractParameter)
                .collect(Collectors.toUnmodifiableList());

        return new Request(commands.get(command), parameters);
    }
}
