package metro.services;

import metro.command.Command;

import java.util.Map;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RequestParser {
    private static final Pattern COMMAND = Pattern.compile("/(?<command>\\w+)(\\s+(?<args>.*))?");
    private static final Pattern PARAMS = Pattern.compile("(\\w+)|\"([^\"]+)\"");
    private final Map<String, Command> commands;

    public RequestParser(final Map<String, Command> commands) {
        this.commands = commands;
    }

    public Optional<Runnable> parse(final String userInput) {
        final var matcher = COMMAND.matcher(userInput);

        if (!matcher.matches()) {
            return Optional.empty();
        }
        final var command = matcher.group("command").toLowerCase();

        if (!commands.containsKey(command)) {
            return Optional.empty();
        }

        final var args = matcher.group("args");
        final var parameters = PARAMS.matcher(args).results()
                .map(RequestParser::extractParameter)
                .collect(Collectors.toUnmodifiableList());

        return Optional.of(new Request(commands.get(command), parameters));
    }

    private static String extractParameter(final MatchResult result) {
        return result.group(2) == null ? result.group(1) : result.group(2);
    }
}
