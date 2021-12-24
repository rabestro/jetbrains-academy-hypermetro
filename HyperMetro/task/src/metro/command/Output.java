package metro.command;

import metro.model.MetroStation;
import metro.model.StationID;
import metro.service.MetroService;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static java.lang.System.lineSeparator;
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
        validateParametersNumber(parameters, REQUIRED_ONE);
        return metroService.getMetroLine(parameters.get(SOURCE_LINE))
                .getStations().stream()
                .map(this::printStation)
                .collect(joining(lineSeparator()));
    }

    private String printStation(final MetroStation metroStation) {
        final var name = metroStation.getStationID().name();
        return name + lineSeparator()
                + printNeighbors(PREFIX_PREV, metroStation.getPrev())
                + printNeighbors(PREFIX_NEXT, metroStation.getNext())
                + printNeighbors(PREFIX_TRAN, metroStation.getTransfer());
    }

    private String printNeighbors(final String prefix, final Set<StationID> stations) {
        final Function<StationID, String> name = prefix.equals(PREFIX_TRAN) ? StationID::line : StationID::name;
        return stations.isEmpty() ? "" : stations.stream()
                .map(name.andThen(prefix::concat))
                .collect(joining(lineSeparator())) + lineSeparator();
    }

}
