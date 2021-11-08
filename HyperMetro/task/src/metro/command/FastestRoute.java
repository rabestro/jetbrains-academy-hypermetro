package metro.command;

import metro.model.MetroNode;
import metro.model.StationID;
import metro.service.MetroService;

import java.util.List;
import java.util.StringJoiner;

public class FastestRoute extends HyperMetroCommand {
    public FastestRoute(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> parameters) {
        validateParametersNumber(parameters, 4);
        final var source = new StationID(parameters.get(0), parameters.get(1));
        final var target = new StationID(parameters.get(2), parameters.get(3));
        final var route = metroService.fastestRoute(source, target);
        return printRoute(route);
    }

    private String printRoute(final List<MetroNode> route) {
        var line = route.get(0).getLine();
        final var stringJoiner = new StringJoiner("\n");
        var time = 0;
        for (final var node : route) {
            if (!node.getLine().equals(line)) {
                line = node.getLine();
                stringJoiner.add("Transition to line " + line);
            }
            time += node.getDistance();
            stringJoiner.add(node.getName() + " {" + time + " min}");
        }
        final var totalTime = route.stream().mapToInt(MetroNode::getDistance).sum();
        stringJoiner.add("Total: " + totalTime + " minutes in the way");
        return stringJoiner.toString();
    }
}
