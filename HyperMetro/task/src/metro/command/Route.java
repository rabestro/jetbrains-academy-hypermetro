package metro.command;

import metro.service.MetroService;

import java.util.List;

public class Route extends RouteCommand {
    public Route(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> parameters) {
        final var route = findRoute(parameters, metroService::route);
        return printRoute(route);
    }

}
