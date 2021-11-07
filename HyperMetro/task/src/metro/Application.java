package metro;

import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import metro.model.MetroLine;
import metro.model.MetroMap;
import metro.service.RequestParser;
import metro.ui.UserInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.System.Logger.Level.INFO;
import static java.util.function.Function.identity;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableMap;

@AllArgsConstructor
public class Application {
    private static final System.Logger LOGGER = System.getLogger("HyperMetro");

    private final UserInterface ui;
    private final Predicate<String> exit;
    private final RequestParser requestParser;
    private final MetroMap metroMap;

    public void run(final String fileName) {
        try {
            loadMetroMap(fileName);
            commandLineInterface();
        } catch (IOException exception) {
            ui.printLine(exception.getMessage());
        }
    }

    private void commandLineInterface() {
        LOGGER.log(INFO, "HyperMetro command line interface started");

        Stream.generate(ui::readLine)
                .takeWhile(not(exit))
                .map(requestParser::parse)
                .forEach(Runnable::run);
    }

    private void loadMetroMap(final String fileName) throws IOException {
        LOGGER.log(INFO, "Loading Metro from file: " + fileName);
        final var reader = Files.newBufferedReader(Paths.get(fileName));
        final var json = new JsonParser().parse(reader);
        final var lines = json.getAsJsonObject()
                .entrySet().stream()
                .map(MetroLine::from)
                .collect(toUnmodifiableMap(MetroLine::getLineName, identity()));
        metroMap.setLines(lines);
        LOGGER.log(INFO, "Loaded metro lines: " + lines.keySet());
    }

}
