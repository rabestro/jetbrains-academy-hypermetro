package metro.command;

import metro.algorithm.Node;
import metro.model.StationID;
import metro.service.MetroService;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class FastestRoute extends RouteCommand {
    public FastestRoute(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> parameters) {
        validateParametersNumber(parameters, 4);
        final var source = new StationID(parameters.get(0), parameters.get(1));
        final var target = new StationID(parameters.get(2), parameters.get(3));
        final var route = metroService.fastestRoute(source, target);
        final var totalTime = route.getLast().getDistance();
        return print(route) + "\nTotal: " + totalTime + " minutes in the way";
    }

    String print(final LinkedList<Node<StationID>> route) {
        final var stringJoiner = new StringJoiner("\n");
        var line = route.getFirst().getId().getLine();

        for (final var node : route) {
            if (!node.getId().getLine().equals(line)) {
                line = node.getId().getLine();
                stringJoiner.add("Transition to line " + line);
            }
            stringJoiner.add(node.getId().getName());
        }
        return stringJoiner.toString();
    }
}
