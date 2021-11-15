package metro.command;

import metro.model.StationID;
import metro.service.MetroService;

import java.util.List;

public class Remove extends HyperMetroCommand {
    public Remove(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> parameters) {
        validateParametersNumber(parameters, 2);
        metroService.remove(new StationID(parameters.get(0), parameters.get(1)));
        return "Metro station successfully removed";
    }
}
