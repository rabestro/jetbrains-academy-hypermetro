package metro;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import metro.domain.MetroMap;
import metro.ui.UserInterface;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.function.Consumer;

import static java.lang.System.Logger.Level.INFO;

@AllArgsConstructor
@SpringBootApplication
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SpringBootConsoleApplication implements CommandLineRunner {
    private static final System.Logger LOGGER = System.getLogger("HyperMetro");

    UserInterface ui;
    Consumer<MetroMap> application;

    public static void main(String[] args) {
        LOGGER.log(INFO, "STARTING THE APPLICATION");
        SpringApplication.run(SpringBootConsoleApplication.class, args);
        LOGGER.log(INFO, "APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
        LOGGER.log(INFO, "EXECUTING : command line runner");
        ui.printLine("Start Application ");
        for (int i = 0; i < args.length; ++i) {
            LOGGER.log(INFO, "args[{0}]: {1}", i, args[i]);
        }
        try {
            var metro = MetroMap.from(args[0]);
            ui.printLine(metro.getLines());
            application.accept(metro);
        } catch (IOException exception) {
            ui.printLine(exception.getMessage());
        }
    }
}
