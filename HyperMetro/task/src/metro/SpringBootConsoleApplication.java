package metro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.System.Logger.Level.INFO;

@SpringBootApplication
public class SpringBootConsoleApplication implements CommandLineRunner {
    private static final System.Logger LOGGER = System.getLogger("HyperMetro");

    public static void main(String[] args) {
        LOGGER.log(INFO, "STARTING THE APPLICATION");
        SpringApplication.run(SpringBootConsoleApplication.class, args);
        LOGGER.log(INFO, "APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
        LOGGER.log(INFO, "EXECUTING : command line runner");
        for (int i = 0; i < args.length; ++i) {
            LOGGER.log(INFO, "args[{}]: {}", i, args[i]);
        }
    }
}
