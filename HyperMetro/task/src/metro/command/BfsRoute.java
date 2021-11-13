package metro.command;

import metro.service.MetroService;

import java.util.List;

public class BfsRoute extends RouteCommand {
    public BfsRoute(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> parameters) {
        final var route = findRoute(parameters, metroService::bfsRoute);
        return printRoute(route);
    }
}
