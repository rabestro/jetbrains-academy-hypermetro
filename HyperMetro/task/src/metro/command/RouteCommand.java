package metro.command;

import metro.algorithm.Node;
import metro.model.StationId;
import metro.service.MetroService;

import java.util.Deque;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.BiFunction;

import static java.lang.System.Logger.Level.DEBUG;

abstract class RouteCommand extends HyperMetroCommand {
    private static final System.Logger LOGGER = System.getLogger("RouteCommand");

    RouteCommand(final MetroService metroService) {
        super(metroService);
    }

    Deque<Node<StationId>> findRoute(
            final List<String> parameters,
            final BiFunction<StationId, StationId, Deque<Node<StationId>>> strategy) {
        validateParametersNumber(parameters, REQUIRED_FOUR);
        final var source = new StationId(parameters.get(SOURCE_LINE), parameters.get(SOURCE_NAME));
        final var target = new StationId(parameters.get(TARGET_LINE), parameters.get(TARGET_NAME));
        return strategy.apply(source, target);
    }

    String printRoute(final Deque<Node<StationId>> route) {
        final var stringJoiner = new StringJoiner(System.lineSeparator());
        var line = route.getFirst().getId().line();

        for (final var node : route) {
            if (!node.getId().line().equals(line)) {
                line = node.getId().line();
                stringJoiner.add("Transition to line " + line);
            }
            stringJoiner.add(node.getId().name());
            LOGGER.log(DEBUG, "Station: {0}, distance: {1}", node.getId().name(), node.getDistance());
        }
        return stringJoiner.toString();
    }

}
