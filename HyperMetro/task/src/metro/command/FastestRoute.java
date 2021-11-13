package metro.command;

import metro.model.StationID;
import metro.service.MetroService;

import java.util.List;

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
        return printRoute(route) + "\nTotal: " + totalTime + " minutes in the way";
    }

}
