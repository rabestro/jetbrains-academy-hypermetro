package metro.command;

import metro.model.StationID;
import metro.service.MetroService;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class Route extends RouteCommand {
    public Route(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> parameters) {
        validateParametersNumber(parameters, 4);
        final var source = new StationID(parameters.get(0), parameters.get(1));
        final var target = new StationID(parameters.get(2), parameters.get(3));
        final var route = metroService.route(source, target);
        return print(route);
    }

    String print(final LinkedList<StationID> route) {
        final var stringJoiner = new StringJoiner("\n");
        var line = route.getFirst().getLine();

        for (final var node : route) {
            if (!node.getLine().equals(line)) {
                line = node.getLine();
                stringJoiner.add("Transition to line " + line);
            }
            stringJoiner.add(node.getName());
        }
        return stringJoiner.toString();
    }
}
