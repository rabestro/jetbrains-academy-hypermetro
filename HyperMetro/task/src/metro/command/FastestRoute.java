package metro.command;

import metro.service.MetroService;

import java.util.List;

/**
 * Prints the fastest way by using Dijkstra's algorithm.
 * The travel time between station is taken into account.
 *
 * Transferring from one line to another takes 5 minutes.
 */
public class FastestRoute extends RouteCommand {
    public FastestRoute(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> parameters) {
        final var route = findRoute(parameters, metroService::fastestRoute);
        final var totalTime = route.getLast().getDistance();
        return printRoute(route) + "\nTotal: " + totalTime + " minutes in the way";
    }

}
