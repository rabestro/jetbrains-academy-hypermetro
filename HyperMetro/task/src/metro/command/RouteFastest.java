package metro.command;

import metro.algorithm.SearchAlgorithm;
import metro.algorithm.DijkstrasSearchAlgorithm;
import metro.model.StationId;
import metro.service.MetroService;

import java.util.List;

/**
 * Prints the fastest way by using Dijkstra's algorithm.
 * The travel time between station is taken into account.
 * <p>
 * Transferring from one line to another takes 5 minutes.
 */
public class RouteFastest extends RouteCommand {
    private static final SearchAlgorithm<StationId> algorithm = new DijkstrasSearchAlgorithm<>();

    public RouteFastest(MetroService metroService) {
        super(metroService);
        transferTime = 5;
    }

    @Override
    public String apply(List<String> parameters) {
        return findRoute(parameters, algorithm);
    }
}
