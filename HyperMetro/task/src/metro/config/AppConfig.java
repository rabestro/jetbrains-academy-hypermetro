package metro.config;

import metro.Application;
import metro.command.*;
import metro.model.MetroMap;
import metro.service.*;
import metro.ui.ConsoleInterface;
import metro.ui.UserInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;
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

    @Bean(name = "mapLoader")
    public MapLoader getMapLoader() {
        return new MapLoaderImpl(getMetro());
    }

    @Bean(name = "exit")
    public Predicate<String> exit() {
        return Set.of("/exit", "exit", "quit", "/quit")::contains;
    }

    @Bean(name = "commands")
    public Map<String, Command> getCommands() {
        return Map.of(
                "output", new Output(getMetroService()),
                "print", new Print(getMetroService()),
                "append", new Append(getMetroService()),
                "add-head", new AddHead(getMetroService()),
                "connect", new Connect(getMetroService()),
                "remove", new Remove(getMetroService()),
                "route", new Route(getMetroService()),
                "bfs-route", new BfsRoute(getMetroService()),
                "fastest-route", new FastestRoute(getMetroService())
        );
    }

    @Bean(name = "metro")
    public MetroMap getMetro() {
        return new MetroMap();
    }

    @Bean(name = "requestParser")
    public RequestParser getRequestParser() {
        return new RequestParser(ui(), new ParameterParser(), getCommands());
    }

    @Bean(name = "application")
    public Application getApplication() {
        return new Application(ui(), exit(), getRequestParser(), getMapLoader());
    }
}
