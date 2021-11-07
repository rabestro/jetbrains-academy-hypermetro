package metro;

import metro.command.*;
import metro.model.MetroMap;
import metro.service.MetroService;
import metro.service.MetroServiceImpl;
import metro.service.RequestParser;
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

    @Bean(name = "metroService")
    public MetroService getMetroService() {
        return new MetroServiceImpl(getMetro());
    }

    @Bean(name = "exit")
    public Predicate<String> exit() {
        return "/exit"::equalsIgnoreCase;
    }

    @Bean(name = "invalid")
    public Runnable invalidCommand() {
        return () -> ui().printLine("Invalid command.");
    }

    @Bean(name = "commands")
    public Map<String, Command> getCommands() {
        return Map.of(
                "output", new Output(getMetroService()),
                "append", new Append(getMetroService()),
                "add-head", new AddHead(getMetroService()),
                "connect", new Connect(getMetroService())
        );
    }

    @Bean(name = "metro")
    public MetroMap getMetro() {
        return new MetroMap();
    }

    @Bean(name = "requestParser")
    public RequestParser getRequestParser() {
        return new RequestParser(ui(), getMetro(), getCommands(), invalidCommand());
    }

    @Bean(name = "application")
    public Application getApplication() {
        return new Application(ui(), exit(), getRequestParser(), getMetro());
    }
}
