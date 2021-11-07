package metro.command;

import metro.service.MetroService;

import java.util.List;

public class Route extends HyperMetroCommand{
    public Route(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> parameters) {
        validateParametersNumber(parameters, 4);
        //TODO Implement the command
        return NOT_IMPLEMENTED;
    }
}
