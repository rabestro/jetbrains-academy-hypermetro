package metro;

import lombok.AllArgsConstructor;
import metro.command.ActionParser;
import metro.model.MetroMap;
import metro.ui.UserInterface;

import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.System.Logger.Level.INFO;
import static java.util.function.Predicate.not;

@AllArgsConstructor
public class Application implements Runnable {
    private static final System.Logger LOGGER = System.getLogger("HyperMetro");

    private final UserInterface ui;
    private final Predicate<String> exit;
    private final ActionParser requestParser;
    private final MetroMap metroMap;

    @Override
    public void run() {
        LOGGER.log(INFO, "HyperMetro command line interface started");

        Stream.generate(ui::readLine)
                .takeWhile(not(exit))
                .map(requestParser::parse)
                .forEach(Runnable::run);
    }

}
