package metro;

import lombok.AllArgsConstructor;
import metro.service.MapLoader;
import metro.service.RequestParser;
import metro.ui.UserInterface;

import java.io.IOException;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.System.Logger.Level.INFO;
import static java.util.function.Predicate.not;

@AllArgsConstructor
public class Application {
    private static final System.Logger LOGGER = System.getLogger("HyperMetro");

    private final UserInterface ui;
    private final Predicate<String> exit;
    private final RequestParser requestParser;
    private final MapLoader mapLoader;

    public void run(final String fileName) {
        try {
            mapLoader.load(fileName);
            LOGGER.log(INFO, "HyperMetro started");

            Stream.generate(ui::readLine)
                    .takeWhile(not(exit))
                    .map(requestParser::parse)
                    .forEach(Runnable::run);

        } catch (IOException exception) {
            ui.printLine(exception.getMessage());
        }
    }

}
