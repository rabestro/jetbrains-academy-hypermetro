package metro.command;

import metro.model.MetroNode;
import metro.model.StationID;
import metro.service.MetroService;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.BiFunction;

abstract class RouteCommand extends HyperMetroCommand {
    private static final System.Logger LOGGER = System.getLogger("RouteCommand");

    RouteCommand(final MetroService metroService) {
        super(metroService);
    }

    LinkedList<MetroNode> findRoute(
            final List<String> parameters,
            final BiFunction<StationID, StationID, LinkedList<MetroNode>> strategy
    ) {
        validateParametersNumber(parameters, 4);
        final var source = new StationID(parameters.get(0), parameters.get(1));
        final var target = new StationID(parameters.get(2), parameters.get(3));
        return strategy.apply(source, target);
    }

    String printRoute(final LinkedList<MetroNode> route) {
        final var stringJoiner = new StringJoiner("\n");
        var line = route.getFirst().getLine();

        for (final var node : route) {
            if (!node.getLine().equals(line)) {
                line = node.getLine();
                stringJoiner.add("Transition to line " + line);
            }
            LOGGER.log(System.Logger.Level.INFO, String.format("[%3d] %s", node.getDistance(), node.getName()));
            stringJoiner.add(node.getName());
        }
        return stringJoiner.toString();
    }
}
