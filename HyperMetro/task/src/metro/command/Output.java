package metro.command;

import metro.model.MetroStation;
import metro.model.StationID;
import metro.service.MetroService;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;

public class Output extends HyperMetroCommand {
    private static final String PREFIX_PREV = "<---| ";
    private static final String PREFIX_NEXT = "--->| ";
    private static final String PREFIX_TRAN = "<---> ";

    public Output(final MetroService metroService) {
        super(metroService);
    }

    @Override
    public String apply(final List<String> parameters) {
        validateParametersNumber(parameters, 1);
        return metroService.getMetroLine(parameters.get(0))
                .getStations().stream()
                .map(this::printStation)
                .collect(joining(System.lineSeparator()));
    }

    private String printStation(final MetroStation metroStation) {
        final var name = metroStation.getStationID().getName();
        return name + System.lineSeparator()
                + printNeighbors(PREFIX_PREV, metroStation.getPrev())
                + printNeighbors(PREFIX_NEXT, metroStation.getNext())
                + printNeighbors(PREFIX_TRAN, metroStation.getTransfer());
    }

    private String printNeighbors(final String prefix, final Set<StationID> stations) {
        final Function<StationID, String> name = prefix.equals(PREFIX_TRAN) ? StationID::getLine : StationID::getName;
        return stations.isEmpty() ? "" : stations.stream()
                .map(name.andThen(prefix::concat))
                .collect(joining(System.lineSeparator())) + System.lineSeparator();
    }

}
