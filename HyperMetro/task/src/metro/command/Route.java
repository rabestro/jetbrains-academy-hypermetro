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
 * Transactions between lines is not considered as moving around the nodes of the graph.
 */
public class Route extends RouteCommand {
    private static final Algorithm<StationId> algorithm = new DijkstrasAlg<>();

    public Route(final MetroService metroService) {
        super(metroService);
        transferTime = 0;
    }

    @Override
    public String apply(final List<String> parameters) {
        return findRoute(parameters, algorithm);
    }

}
