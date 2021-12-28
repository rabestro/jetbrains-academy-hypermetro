package metro.command;

import metro.service.MetroService;

import java.util.List;

/**
 * Prints the fastest way by using Dijkstra's algorithm.
 * The travel time between station is taken into account.
 * Transferring from one line to another takes 5 minutes.
 * <p>
 * The program prints the estimate total travel time.
 */
public class RouteFastest extends RouteCommand {
    public RouteFastest(MetroService metroService) {
        super(metroService);
        hideTime = false;
    }

    @Override
    public String apply(List<String> parameters) {
        return findRoute(parameters, DIJKSTRAS);
    }
}
