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
        return metroService.getLineStations(parameters.get(0)).stream()
                .map(this::printStation)
                .collect(joining("\n", DEPOT + '\n', '\n' + DEPOT));
    }

    private String printStation(final MetroStation metroStation) {
        final var name = metroStation.getStationID().getName();
        final var transfer = transferToString(metroStation.getTransfer());
        return name + transfer;
    }

    private String transferToString(final Set<StationID> transferStations) {
        return transferStations.stream()
                .map(station -> " - " + station.getName() + " (" + station.getLine() + " line)")
                .collect(joining());
    }

}
