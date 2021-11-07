package metro.service;

import lombok.AllArgsConstructor;
import metro.model.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

@AllArgsConstructor
public class MetroServiceImpl implements MetroService {
    private final MetroMap metroMap;

    @Override
    public Optional<MetroLine> findMetroLine(final String name) {
        return metroMap.getLine(name);
    }

    public MetroLine getMetroLine(final String name) {
        return metroMap.getLine(name).orElseThrow(() -> new NoSuchElementException(
                "There is no metro line with a name '" + name + "'"));
    }

    public MetroStation getMetroStation(final StationID stationID) {
        return getMetroLine(stationID.getLine()).getStation(stationID.getName()).orElseThrow(
                () -> new NoSuchElementException("There is no station '" + stationID.getName()
                        + "' on the metro line '" + stationID.getLine() + "'"));
    }

    public MetroStation getMetroStation(final String line, final String name) {
        return getMetroLine(line).getStation(name)
                .orElseThrow(() -> new NoSuchElementException(
                        "There is no station '" + name + "' on the metro line '" + line + "'"));
    }

    public Collection<MetroStation> getLineStations(final String name) {
        return metroMap.getLine(name)
                .orElseThrow(() -> new NoSuchElementException(
                        "There is no metro line with a name '" + name + "'"))
                .getStations();
    }

    public void addHead(final String lineName, final String stationName) {
        getMetroLine(lineName).addHead(stationName);
    }

    public void append(final String lineName, final String stationName) {
        getMetroLine(lineName).append(stationName);
    }

    public void remove(final String lineName, final String stationName) {
        final var station = getMetroStation(lineName, stationName);
        getMetroLine(lineName).remove(station);
    }

    public void connect(final StationID s1, final StationID s2) {
        final var source = getMetroStation(s1);
        final var target = getMetroStation(s2);
        source.setTransfer(Set.of(s2));
        target.setTransfer(Set.of(s1));
    }

    public List<StationID> route(final StationID source, final StationID target) {
        final var queue = new LinkedList<PathStep>();
        final var visited = new HashSet<StationID>();

        queue.add(new PathStep(getMetroStation(source), null));
        while (!queue.isEmpty()) {
            final var step = queue.pollFirst();
            final var sid = step.getStation().getStationID();
            if (sid.equals(target)) {
                final var path = new LinkedList<StationID>();
                Stream.iterate(step, Objects::nonNull, PathStep::getPrevious)
                        .map(PathStep::getStation)
                        .map(MetroStation::getStationID)
                        .forEach(path::addFirst);
                return path;
            }
            visited.add(sid);
            step.getStation().getNeighbors().stream()
                    .filter(Predicate.not(visited::contains))
                    .map(this::getMetroStation)
                    .map(s -> new PathStep(s, step))
                    .forEach(queue::add);
        }
        return List.of();
    }
}
