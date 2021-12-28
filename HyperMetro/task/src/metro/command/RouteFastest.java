package metro.command;

import metro.algorithm.Algorithm;
import metro.algorithm.DijkstrasAlg;
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
    private static final Algorithm<StationId> algorithm = new DijkstrasAlg<>();

    public RouteFastest(MetroService metroService) {
        super(metroService);
        transferTime = 5;
    }

    @Override
    public String apply(List<String> parameters) {
        return findRoute(parameters, algorithm);
    }
}
