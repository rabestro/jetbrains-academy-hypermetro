package metro.command;

import metro.model.StationID;
import metro.service.MetroService;

import java.util.List;
import java.util.StringJoiner;

public class Route extends HyperMetroCommand {
    public Route(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> parameters) {
        validateParametersNumber(parameters, 4);
        final var source = new StationID(parameters.get(0), parameters.get(1));
        final var target = new StationID(parameters.get(2), parameters.get(3));
        final var route = metroService.route(source, target);
        return printRoute(route);
    }

    private String printRoute(final List<StationID> route) {
        if (route.isEmpty()) {
            return "Couldn't find path";
        }
        final var stringJoiner = new StringJoiner("\n");
        var line = route.get(0).getLine();

        for (final var sid : route) {
            if (!sid.getLine().equals(line)) {
                line = sid.getLine();
                stringJoiner.add("Transition to line " + line);
            }
            stringJoiner.add(sid.getName());
        }
        return stringJoiner.toString();
    }
}
