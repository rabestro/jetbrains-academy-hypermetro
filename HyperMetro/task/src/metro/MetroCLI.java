package metro;

import metro.command.Command;
import metro.entity.Metro;
import metro.services.RequestParser;
import metro.ui.ConsoleInterface;
import metro.ui.UserInterface;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MetroCLI implements Runnable {
    private static final String EXIT = "/exit";

    private final Metro metro;
    private final UserInterface ui;
    private final Map<String, Command> commands;
    private final RequestParser requestParser;

    public MetroCLI(final Metro metro,
                    final ConsoleInterface ui,
                    final Set<Command> commands) {
        this.ui = ui;
        this.metro = metro;
        this.commands = commands.stream()
                .collect(Collectors.toUnmodifiableMap(Command::name, Function.identity()));
        this.requestParser = new RequestParser(this.commands);
    }

    @Override
    public void run() {
        Stream.generate(ui::readLine)
                .takeWhile(Predicate.not(EXIT::equalsIgnoreCase))
                .map(requestParser::parse)
                .map(optional -> optional.orElse(this::printError))
                .forEach(Runnable::run);
    }

    private void printError() {
        ui.printLine("Invalid command.");
    }
}
