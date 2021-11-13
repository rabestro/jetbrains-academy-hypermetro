package metro.service;

import lombok.AllArgsConstructor;
import metro.algorithm.BreadthFirstSearchAlgorithm;
import metro.algorithm.DijkstrasAlgorithm;
import metro.algorithm.Node;
import metro.model.MetroLine;
import metro.model.MetroMap;
import metro.model.MetroStation;
import metro.model.StationID;

import java.util.*;
import java.util.stream.Stream;

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
    public LinkedList<Node<StationID>> bfsRoute(final StationID source, final StationID target) {
        final Stream<Node<StationID>> nodes = metroMap.stream().map(SimpleNode::new);
        final var strategy = new BreadthFirstSearchAlgorithm<>(nodes);
        return strategy.findRoute(source, target);
    }

    @Override
    public LinkedList<Node<StationID>> route(final StationID source, final StationID target) {
        final Stream<Node<StationID>> nodes = metroMap.stream().map(MetroNode::new);
        final var strategy = new DijkstrasAlgorithm<>(nodes);
        return strategy.findRoute(source, target);
    }

    @Override
    public LinkedList<Node<StationID>> fastestRoute(final StationID source, final StationID target) {
        final Stream<Node<StationID>> nodes = metroMap.stream().map(TimeNode::new);
        final var strategy = new DijkstrasAlgorithm<>(nodes);
        return strategy.findRoute(source, target);
    }

    private class MetroNode extends Node<StationID> {
        private MetroNode(final StationID stationID) {
            super(stationID);
        }

        @Override
        protected Map<StationID, Integer> getNeighbors() {
            final var neighbors = new HashMap<StationID, Integer>();
            final var station = metroMap.getStation(getId()).orElseThrow();
            station.getNext().forEach(id -> neighbors.put(id, 1));
            station.getPrev().forEach(id -> neighbors.put(id, 1));
            station.getTransfer().forEach(id -> neighbors.put(id, 0));
            return neighbors;
        }
    }

    private class SimpleNode extends Node<StationID> {
        private SimpleNode(final StationID stationID) {
            super(stationID);
        }

        @Override
        protected Map<StationID, Integer> getNeighbors() {
            final var neighbors = new HashMap<StationID, Integer>();
            final var station = metroMap.getStation(getId()).orElseThrow();
            station.getNext().forEach(id -> neighbors.put(id, 1));
            station.getPrev().forEach(id -> neighbors.put(id, 1));
            station.getTransfer().forEach(id -> neighbors.put(id, 1));
            return neighbors;
        }
    }

    private class TimeNode extends Node<StationID> {
        private TimeNode(final StationID stationID) {
            super(stationID);
        }

        @Override
        protected Map<StationID, Integer> getNeighbors() {
            final var neighbors = new HashMap<StationID, Integer>();
            final var station = metroMap.getStation(getId()).orElseThrow();
            station.getNext().forEach(id -> neighbors.put(id, station.getTime()));
            station.getTransfer().forEach(id -> neighbors.put(id, TRANSFER_TIME));
            station.getPrev().forEach(id -> neighbors.put(id, getMetroStation(id).getTime()));
            return neighbors;
        }
    }
}
