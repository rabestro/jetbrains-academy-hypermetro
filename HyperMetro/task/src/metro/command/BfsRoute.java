package metro.command;

import metro.service.MetroService;

import java.util.List;

/**
 * Prints the shortest (the smallest number of stations) way from one station to another.
 * Breadth-First search algorithm is used.
 */
public class BfsRoute extends RouteCommand {
    public BfsRoute(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> parameters) {
        final var route = findRoute(parameters, metroService::bfsRoute);
        return printRoute(route);
    }
}
