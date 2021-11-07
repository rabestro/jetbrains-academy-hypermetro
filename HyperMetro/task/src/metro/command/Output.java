package metro.command;

import lombok.AllArgsConstructor;
import metro.model.MetroLine;
import metro.model.MetroMap;
import metro.model.MetroStation;
import metro.model.StationID;
import metro.ui.UserInterface;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Output implements Command {
    private static final String DEPOT = "depot";

    private final UserInterface ui;

    @Override
    public void accept(final MetroMap metroMap, final List<String> parameters) {
        Command.checkParametersNumber(parameters, 1);
        final var metroLineName = parameters.get(0);
        final var metroLine = metroMap.getLine(metroLineName).orElseThrow();
        printLine(metroLine);
    }

    private void printLine(final MetroLine metroLine) {
        ui.printLine(DEPOT);
        metroLine.forEach(this::printStation);
        ui.printLine(DEPOT);
    }

    private void printStation(final MetroStation metroStation) {
        final var name = metroStation.getStationID().getName();
        final var transfer = transferToString(metroStation.getTransfer());
        ui.printLine(name + transfer);
    }

    private String transferToString(final Set<StationID> transferStations) {
        return transferStations.stream()
                .map(station -> " - " + station.getName() + " (" + station.getLine() + " line)")
                .collect(Collectors.joining());
    }
}
