package metro.service;

import lombok.AllArgsConstructor;
import metro.model.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

@AllArgsConstructor
public class MetroServiceImpl implements MetroService {
    private static final System.Logger LOGGER = System.getLogger("MetroService");
    private static final int TRANSFER_TIME = 5;

    private final MetroMap metroMap;

    @Override
    public MetroLine getMetroLine(final String name) {
        return metroMap.getLine(name).orElseThrow(() -> new NoSuchElementException(
                "There is no metro line with a name '" + name + "'"));
    }

    @Override
    public MetroStation getMetroStation(final StationID station) {
        return getMetroLine(station.getLine()).getStation(station.getName())
                .orElseThrow(() -> new NoSuchElementException(no(station).get()));
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

    private Supplier<String> no(final StationID station) {
        return () -> "There is no station '" + station.getName()
                + "' on the metro line '" + station.getLine() + "'";
    }

    @Override
    public LinkedList<MetroNode> route(final StationID source, final StationID target) {
        final var queue = new LinkedList<MetroNode>();
        final var visited = new HashSet<StationID>();
        queue.add(new MetroNode(getMetroStation(source)));
        while (!queue.isEmpty()) {
            final var step = queue.pollFirst();
            final var sid = step.getStation().getStationID();
            if (sid.equals(target)) {
                return buildRoute(step);
            }
            visited.add(sid);
            step.getStation().getNeighbors().stream()
                    .filter(Predicate.not(visited::contains))
                    .map(this::getMetroStation)
                    .map(MetroNode::new)
                    .peek(n -> n.setPrevious(step))
                    .forEach(queue::add);
        }
        return new LinkedList<>();
    }

    public LinkedList<MetroNode> fastestRoute(final StationID source, final StationID target) {
        final var nodes = metroMap.getNodes();
        final var sourceNode = requireNonNull(nodes.get(source), no(source));
        final var targetNode = requireNonNull(nodes.get(target), no(target));
        sourceNode.setDistance(0);
        final var queue = new LinkedList<MetroNode>();
        queue.add(sourceNode);

        while (!queue.isEmpty()) {
            final var node = queue.pollFirst();
            final var neighbors = getNeighbors(node.getStation());
            neighbors.forEach((id, time) -> {
                final var distance = node.getDistance() + time;
                final var neighbor = nodes.get(id);
                if (neighbor.noVisited()) {
                    queue.add(neighbor);
                }
                if (distance < neighbor.getDistance()) {
                    neighbor.setPrevious(node);
                    neighbor.setDistance(distance);
                }
            });
        }
        return buildRoute(targetNode);
    }

    private LinkedList<MetroNode> buildRoute(final MetroNode target) {
        final var route = new LinkedList<MetroNode>();
        Stream.iterate(target, Objects::nonNull, MetroNode::getPrevious).forEach(route::addFirst);
        return route;
    }

    public Map<StationID, Integer> getNeighbors(final MetroStation station) {
        final var neighbors = new HashMap<StationID, Integer>();
        station.getNext().forEach(id -> neighbors.put(id, station.getTime()));
        station.getTransfer().forEach(id -> neighbors.put(id, TRANSFER_TIME));
        station.getPrev().forEach(id -> neighbors.put(id, getMetroStation(id).getTime()));
        return neighbors;
    }

}
