package metro;

import lombok.AllArgsConstructor;
import metro.repository.MetroRepository;
import metro.controller.Broker;
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
    private final Broker broker;
    private final MetroRepository repository;

    public void start(final String fileName) {
        LOGGER.log(INFO, "HyperMetro started");
        try {
            repository.load(fileName);
        } catch (IOException exception) {
            ui.write(exception.getMessage());
            return;
        }

        commandLineInterface();

        LOGGER.log(INFO, "HyperMetro finished");
    }

    private void commandLineInterface() {
        Stream.generate(ui::read).takeWhile(not(exit)).map(broker).forEach(ui::write);
    }
}
