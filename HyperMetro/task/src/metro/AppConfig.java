package metro;

import metro.action.Action;
import metro.action.ActionParser;
import metro.action.Output;
import metro.model.MetroMap;
import metro.ui.ConsoleInterface;
import metro.ui.UserInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.Predicate;

@Configuration
public class AppConfig {

    @Bean(name = "ui")
    public UserInterface ui() {
        return new ConsoleInterface();
    }

    @Bean(name = "exit")
    public Predicate<String> exit() {
        return "/exit"::equalsIgnoreCase;
    }

    @Bean(name = "invalid")
    public Runnable invalidCommand() {
        return () -> ui().printLine("Invalid command.");
    }

    @Bean(name = "output")
    public Action getOutput() {
        return new Output(ui());
    }

    @Bean(name = "commands")
    public Map<String, Action> getCommands() {
        return Map.of("output", getOutput());
    }

    @Bean(name = "metro")
    public MetroMap getMetro() {
        return new MetroMap();
    }

    @Bean(name = "requestParser")
    public ActionParser getRequestParser() {
        return new ActionParser(ui(), getMetro(), getCommands(), invalidCommand());
    }

    @Bean(name = "application")
    public Runnable getApplication() {
        return new Application(ui(), exit(), getRequestParser(), getMetro());
    }
}
