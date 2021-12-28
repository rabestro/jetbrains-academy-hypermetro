package metro.command;

import metro.service.MetroService;

import java.util.List;

/**
 * Prints the shortest (the smallest number of stations) way from one station to another.
 * <p>
 * Breadth-First search algorithm is used.
 * <p>
 * The traveling time between station is not taken into account.
 */
public class RouteShortest extends RouteCommand {

    public RouteShortest(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> parameters) {
        return findRoute(parameters, BFS_ALGORITHM);
    }

}
