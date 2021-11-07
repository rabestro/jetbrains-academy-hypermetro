package metro.command;

import metro.model.StationID;
import metro.service.MetroService;

import java.util.List;

public class Connect extends HyperMetroCommand {
    public Connect(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> parameters) {
        validateParametersNumber(parameters, 4);

        final var source = new StationID(parameters.get(0), parameters.get(1));
        final var target = new StationID(parameters.get(2), parameters.get(3));

        metroService.connect(source, target);

        return "Metro stations successfully connected";
    }
}
