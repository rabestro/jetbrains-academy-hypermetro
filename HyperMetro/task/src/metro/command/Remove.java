package metro.command;

import metro.service.MetroService;

import java.util.List;

public class Remove extends HyperMetroCommand{
    public Remove(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> parameters) {
        validateParametersNumber(parameters, 2);
        metroService.remove(parameters.get(0), parameters.get(1));
        return "Metro station successfully removed";
    }
}
