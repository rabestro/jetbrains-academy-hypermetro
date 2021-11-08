package metro.service;

import lombok.AllArgsConstructor;
import metro.model.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

@AllArgsConstructor
public class MetroServiceImpl implements MetroService {
    private static final int TRANSFER_TIME = 5;

    private final MetroMap metroMap;

    @Override
    public MetroLine getMetroLine(final String name) {
        return metroMap.getLine(name).orElseThrow(() -> new NoSuchElementException(
                "There is no metro line with a name '" + name + "'"));
    }

    @Override
    public MetroStation getMetroStation(final StationID stationId) {
        return getMetroLine(stationId.getLine()).getStation(stationId.getName()).orElseThrow(
                () -> new NoSuchElementException("There is no station '" + stationId.getName()
                        + "' on the metro line '" + stationId.getLine() + "'"));
    }

    @Override
    public Collection<MetroStation> getLineStations(final String name) {
        return metroMap.getLine(name)
                .orElseThrow(() -> new NoSuchElementException(
                        "There is no metro line with a name '" + name + "'"))
                .getStations();
    }

    @Override
    public void addHead(final String lineName, final String stationName) {
        getMetroLine(lineName).addHead(stationName);
    }

    @Override
    public void append(final String lineName, final String stationName) {
        getMetroLine(lineName).append(stationName);
    }

    @Override
    public void remove(final StationID target) {
        getMetroLine(target.getLine()).remove(getMetroStation(target));
    }

    @Override
    public void connect(final StationID source, final StationID target) {
        getMetroStation(source).setTransfer(Set.of(target));
        getMetroStation(target).setTransfer(Set.of(source));
    }

    public List<MetroNode> fastestRoute(final StationID source, final StationID target) {
        return List.of();
    }

    public Map<StationID, Integer> getNeighbors(final MetroStation station) {
        final var neighbors = new HashMap<StationID, Integer>();
        station.getNext().forEach(id -> neighbors.put(id, station.getTime()));
        station.getTransfer().forEach(id -> neighbors.put(id, TRANSFER_TIME));
        station.getPrev().forEach(id-> neighbors.put(id, getMetroStation(id).getTime()));
        return neighbors;
    }

    @Override
    public List<StationID> route(final StationID source, final StationID target) {
        final var queue = new LinkedList<PathStep>();
        final var visited = new HashSet<StationID>();

        queue.add(new PathStep(getMetroStation(source), null));
        while (!queue.isEmpty()) {
            final var step = queue.pollFirst();
            final var sid = step.getStation().getStationID();
            if (sid.equals(target)) {
                return createRoute(step);
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

    private List<StationID> createRoute(final PathStep pathStep) {
        final var path = new LinkedList<StationID>();
        Stream.iterate(pathStep, Objects::nonNull, PathStep::getPrevious)
                .map(PathStep::getStation)
                .map(MetroStation::getStationID)
                .forEach(path::addFirst);
        return path;
    }
}
