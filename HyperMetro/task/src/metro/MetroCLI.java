package metro;

import metro.entity.Metro;
import metro.entity.Request;
import metro.services.RequestParser;
import metro.ui.ConsoleInterface;
import metro.ui.UserInterface;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

@SuppressWarnings("squid:S106")
public class MetroCLI implements Runnable {
    private static final String EXIT = "exit";
    private static final String ERROR = "error";
    private static final Request INVALID_REQUEST = new Request(ERROR, Collections.emptyList());

    private final Metro metro;
    private final UserInterface ui;
    private final Map<String, Consumer<String>> commands = Map.of(
            "append", this::append
    );

    private void append(final String parameters) {

    }

    public MetroCLI(final Metro metro, final ConsoleInterface ui) {
        this.metro = metro;
        this.ui = ui;
    }

    @Override
    public void run() {
        Stream.generate(ui::readLine)
                .map(RequestParser::parse)
                .map(optional -> optional.orElse(INVALID_REQUEST))
                .takeWhile(request -> request.command().equalsIgnoreCase(EXIT))
                .forEach(this::processRequest);
    }

    private void invalidRequest() {

    }

    private void invalidParameters() {

    }

    private void processRequest(final Request request) {


    }
}
