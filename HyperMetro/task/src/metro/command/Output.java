package metro.command;

import metro.model.MetroStation;
import metro.model.StationID;
import metro.service.MetroService;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.joining;

public class Output extends HyperMetroCommand {
    private static final String DEPOT = "depot";

    public Output(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> parameters) {
        validateParametersNumber(parameters, 1);
        return metroService.getMetroLine(parameters.get(0))
                .getStations().stream()
                .map(this::printStation)
                .collect(joining("\n", DEPOT + '\n', '\n' + DEPOT));
    }

    private String printStation(final MetroStation metroStation) {
        final var name = metroStation.getStationID().getName();
        final var transfer = printTransfer(metroStation.getTransfer());
        return name + transfer;
    }

    private String printTransfer(final Set<StationID> transferStations) {
        return transferStations.stream()
                .map(station -> " - " + station.getName() + " (" + station.getLine() + " line)")
                .collect(joining());
    }

}
