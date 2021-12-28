package metro.command;

import metro.algorithm.SearchAlgorithm;
import metro.model.StationId;
import metro.service.MetroService;

import java.util.List;
import java.util.StringJoiner;

import static java.lang.System.Logger.Level.DEBUG;

abstract class RouteCommand extends HyperMetroCommand {
    int transferTime = 5;

    RouteCommand(final MetroService metroService) {
        super(metroService);
    }

    String findRoute(final List<String> parameters, final SearchAlgorithm<StationId> algorithm) {
        validateParametersNumber(parameters, REQUIRED_FOUR);
        final var source = new StationId(parameters.get(SOURCE_LINE), parameters.get(SOURCE_NAME));
        final var target = new StationId(parameters.get(TARGET_LINE), parameters.get(TARGET_NAME));
        final var graph = metroService.getMetroGraph(TRANSFER_TIME);
        final var route = algorithm.findPath(graph, source, target);
        return printRoute(route);
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
}
