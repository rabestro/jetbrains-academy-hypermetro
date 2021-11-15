package metro.command;

import metro.service.MetroService;

import java.util.List;

public class FastestRoute extends RouteCommand {
    public FastestRoute(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> parameters) {
        final var route = findRoute(parameters, metroService::fastestRoute);
        final var totalTime = route.getLast().getDistance();
        return printRoute(route) + "\nTotal: " + totalTime + " minutes in the way";
    }

}
