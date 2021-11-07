package metro.command;

import metro.service.MetroService;

import java.util.List;

public class FastestRoute extends HyperMetroCommand{
    public FastestRoute(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> strings) {
        return NOT_IMPLEMENTED;
    }
}
