package metro;

import lombok.AllArgsConstructor;
import metro.repository.MetroRepository;
import metro.ui.UserInterface;

import java.io.IOException;

import static java.lang.System.Logger.Level.INFO;

@AllArgsConstructor
public class Application {
    private static final System.Logger LOGGER = System.getLogger("HyperMetro");

    private final UserInterface ui;
    private final MetroRepository repository;
    private final Runnable commandLineInterface;

    public void start(final String fileName) {
        LOGGER.log(INFO, "HyperMetro started");
        try {
            repository.load(fileName);
        } catch (IOException exception) {
            ui.write(exception.getMessage());
            return;
        }

        commandLineInterface.run();

        LOGGER.log(INFO, "HyperMetro finished");
    }

}
