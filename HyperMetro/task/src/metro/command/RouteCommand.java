package metro.command;

import metro.algorithm.Node;
import metro.model.StationID;
import metro.service.MetroService;

import java.util.LinkedList;
import java.util.StringJoiner;

import static java.lang.System.Logger.Level.DEBUG;

abstract class RouteCommand extends HyperMetroCommand {
    private static final System.Logger LOGGER = System.getLogger("RouteCommand");

    RouteCommand(final MetroService metroService) {
        super(metroService);
    }

    String printRoute(final LinkedList<Node<StationID>> route) {
        final var stringJoiner = new StringJoiner("\n");
        var line = route.getFirst().getId().getLine();

        for (final var node : route) {
            if (!node.getId().getLine().equals(line)) {
                line = node.getId().getLine();
                stringJoiner.add("Transition to line " + line);
            }
            stringJoiner.add(node.getId().getName());
            LOGGER.log(DEBUG, "Station: {0}, distance: {1}", node.getId().getName(), node.getDistance());
        }
        return stringJoiner.toString();
    }

}
