package metro.command;

import metro.algorithm.Graph;
import metro.algorithm.SearchAlgorithm;
import metro.model.StationId;
import metro.service.MetroService;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.IntStream;

import static java.lang.System.Logger.Level.DEBUG;

abstract class RouteCommand extends HyperMetroCommand {
    int transferTime = 5;
    boolean hideTime = true;

    RouteCommand(final MetroService metroService) {
        super(metroService);
    }

    String findRoute(final List<String> parameters, final SearchAlgorithm<StationId> algorithm) {
        validateParametersNumber(parameters, REQUIRED_FOUR);
        final var source = new StationId(parameters.get(SOURCE_LINE), parameters.get(SOURCE_NAME));
        final var target = new StationId(parameters.get(TARGET_LINE), parameters.get(TARGET_NAME));
        final var graph = metroService.getMetroGraph(TRANSFER_TIME);
        final var route = algorithm.findPath(graph, source, target);
        return printRoute(route) + printTime(graph, route);
    }

    String printRoute(final List<StationId> route) {
        final var stringJoiner = new StringJoiner(System.lineSeparator());
        var line = route.get(0).line();

        for (final var node : route) {
            if (!node.line().equals(line)) {
                line = node.line();
                stringJoiner.add("Transition to line " + line);
            }
            stringJoiner.add(node.name());
            LOGGER.log(DEBUG, "Station: {0}", node.name());
        }
        return stringJoiner.toString();
    }

    String printTime(Graph<StationId> graph, List<StationId> route) {
        if (hideTime) {
            return "";
        }
        final var time = IntStream.range(1, route.size())
                .mapToObj(i -> graph.edges(route.get(i - 1)).get(route.get(i)))
                .mapToInt(Number::intValue)
                .sum();
        return System.lineSeparator() + "Total: " + time + " minutes in the way";
    }
}
