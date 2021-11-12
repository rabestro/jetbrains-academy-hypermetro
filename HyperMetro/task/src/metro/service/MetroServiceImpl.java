package metro.service;

import lombok.AllArgsConstructor;
import metro.algorithm.BreadthFirstSearchAlgorithm;
import metro.algorithm.Node;
import metro.model.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.lang.System.Logger.Level.DEBUG;
import static java.lang.System.Logger.Level.INFO;
import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

@AllArgsConstructor
public class MetroServiceImpl implements MetroService {
    private static final System.Logger LOGGER = System.getLogger("MetroService");
    private static final String NOT_FOUND = "No such metro station or metro line was found.";
    private static final NoSuchElementException NOT_FOUND_EXCEPTION = new NoSuchElementException(NOT_FOUND);
    private static final int TRANSFER_TIME = 5;

    private final MetroMap metroMap;

    @Override
    public MetroLine getMetroLine(final String name) {
        return metroMap.getLine(name).orElseThrow(() -> NOT_FOUND_EXCEPTION);
    }

    @Override
    public MetroStation getMetroStation(final StationID station) {
        return metroMap.getStation(station).orElseThrow(() -> NOT_FOUND_EXCEPTION);
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

    @Override
    public LinkedList<StationID> route(final StationID source, final StationID target) {
        final Map<StationID, Node<StationID>> nodes = metroMap.getAllStations().stream()
                .map(MetroStation::getStationID)
                .map(SimpleNode::new)
                .collect(toUnmodifiableMap(SimpleNode::getId, identity()));

        final var strategy = new BreadthFirstSearchAlgorithm<>(nodes);

        return strategy.findRoute(source, target);
    }

    @Override
    public LinkedList<MetroNode> fastestRoute(final StationID source, final StationID target) {
        return findRoute(source, target, this::getNeighbors);
    }

    private LinkedList<MetroNode> findRoute(
            final StationID source,
            final StationID target,
            final Function<MetroStation, Map<StationID, Integer>> strategy) {
        final var nodes = metroMap.getNodes();
        final var sourceNode = requireNonNull(nodes.get(source), NOT_FOUND);
        final var targetNode = requireNonNull(nodes.get(target), NOT_FOUND);
        sourceNode.setDistance(0);
        final var queue = new LinkedList<MetroNode>();
        queue.add(sourceNode);

        while (!queue.isEmpty()) {
            final var node = queue.pollFirst();
            final var neighbors = strategy.apply(node.getStation());
            neighbors.forEach((id, time) -> {
                final var distance = node.getDistance() + time;
                final var neighbor = nodes.get(id);
                if (neighbor.notVisited()) {
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

    class SimpleNode extends Node<StationID> {
        protected SimpleNode(final StationID stationID) {
            super(stationID);
        }

        @Override
        protected Set<StationID> getNeighbors() {
            LOGGER.log(INFO, getId());
            final var neighbors = new HashSet<StationID>();
            final var station = metroMap.getStation(getId()).orElseThrow();
            neighbors.addAll(station.getNext());
            neighbors.addAll(station.getPrev());
            station.getTransfer().forEach(stationID -> {
                final var transfer = metroMap.getStation(stationID).orElseThrow();
                neighbors.addAll(transfer.getNext());
                neighbors.addAll(transfer.getPrev());
            });
            LOGGER.log(DEBUG, neighbors);
            return neighbors;
        }
    }
}
