package metro.service;

import lombok.AllArgsConstructor;
import metro.algorithm.BreadthFirstSearchAlgorithm;
import metro.algorithm.DijkstrasAlgorithm;
import metro.algorithm.Node;
import metro.model.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.Logger.Level.DEBUG;
import static java.lang.System.Logger.Level.INFO;
import static java.util.Objects.requireNonNull;

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
    public LinkedList<Node<StationID>> route(final StationID source, final StationID target) {
        final Set<Node<StationID>> nodes = metroMap.stream().map(SimpleNode::new)
                .collect(Collectors.toUnmodifiableSet());

        final var strategy = new BreadthFirstSearchAlgorithm<>(nodes);

        return strategy.findRoute(source, target);
    }

    @Override
    public LinkedList<Node<StationID>> fastestRoute(final StationID source, final StationID target) {
        final Set<Node<StationID>> nodes = metroMap.stream().map(TimeNode::new)
                .collect(Collectors.toUnmodifiableSet());

        final var strategy = new DijkstrasAlgorithm<>(nodes);

        return strategy.findRoute(source, target);
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

    private class SimpleNode extends Node<StationID> {
        private SimpleNode(final StationID stationID) {
            super(stationID);
        }

        @Override
        protected Map<StationID, Integer> getNeighbors() {
            LOGGER.log(INFO, getId());
            final var neighbors = new HashMap<StationID, Integer>();
            final var station = metroMap.getStation(getId()).orElseThrow();
            station.getNext().forEach(id -> neighbors.put(id, 1));
            station.getPrev().forEach(id -> neighbors.put(id, 1));
            station.getTransfer().forEach(id -> neighbors.put(id, 1));
            LOGGER.log(DEBUG, neighbors);
            return neighbors;
        }
    }

    private class TimeNode extends Node<StationID> {
        private TimeNode(final StationID stationID) {
            super(stationID);
        }

        @Override
        protected Map<StationID, Integer> getNeighbors() {
            LOGGER.log(INFO, getId());
            final var neighbors = new HashMap<StationID, Integer>();
            final var station = metroMap.getStation(getId()).orElseThrow();
            station.getNext().forEach(id -> neighbors.put(id, station.getTime()));
            station.getTransfer().forEach(id -> neighbors.put(id, TRANSFER_TIME));
            station.getPrev().forEach(id -> neighbors.put(id, getMetroStation(id).getTime()));
            LOGGER.log(DEBUG, neighbors);
            return neighbors;
        }
    }
}
