package metro.service;

import lombok.AllArgsConstructor;
import metro.algorithm.BreadthFirstSearch;
import metro.algorithm.DijkstrasAlgorithm;
import metro.algorithm.Graph;
import metro.algorithm.Node;
import metro.algorithm.SearchAlgorithm;
import metro.model.MetroLine;
import metro.model.MetroStation;
import metro.model.StationID;
import metro.repository.MetroRepository;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.System.Logger.Level.DEBUG;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

@AllArgsConstructor
public class MetroServiceImpl implements MetroService {
    private static final System.Logger LOG = System.getLogger("HyperMetro");
    private static final String NOT_FOUND = "No such metro station or metro line was found.";
    private static final Supplier<NoSuchElementException> NOT_FOUND_EXCEPTION = () -> new NoSuchElementException(NOT_FOUND);
    private static final int TRANSFER_TIME = 5;

    private final MetroRepository repository;

    @Override
    public MetroLine getMetroLine(final String name) {
        return repository.getLine(name).orElseThrow(NOT_FOUND_EXCEPTION);
    }

    @Override
    public MetroStation getMetroStation(final StationID station) {
        return repository.getStation(station).orElseThrow(NOT_FOUND_EXCEPTION);
    }

    @Override
    public void addHead(final String lineName, final String stationName) {
        LOG.log(DEBUG, "AddHead station [{1}] to line [{0}]", lineName, stationName);
        getMetroLine(lineName).addHead(stationName);
    }

    @Override
    public void append(final String lineName, final String stationName) {
        LOG.log(DEBUG, "Append station [{1}] to line [{0}]", lineName, stationName);
        getMetroLine(lineName).append(stationName);
    }

    @Override
    public void remove(final StationID target) {
        LOG.log(DEBUG, "Remove station [{0}]", target);
        getMetroLine(target.line()).remove(getMetroStation(target));
    }

    @Override
    public void connect(final StationID source, final StationID target) {
        LOG.log(DEBUG, "Connect station [{0}] to [{1}]", target);
        getMetroStation(source).setTransfer(Set.of(target));
        getMetroStation(target).setTransfer(Set.of(source));
    }

    @Override
    public List<StationID> bfsRoute(final StationID sourceId, final StationID targetId) {
        final var graph = new Graph<>(repository.stream().collect(toUnmodifiableMap(identity(), this::getEdges)));
        final var algorithm = new BreadthFirstSearch<StationID>();
        return algorithm.findPath(graph, sourceId, targetId);
    }

    private Map<StationID, Number> getEdges(final StationID id) {
        final var edges = new HashMap<StationID, Number>();
        final var station = getMetroStation(id);
        final Consumer<StationID> equalTime = target -> edges.put(target, 1);
        station.getNext().forEach(equalTime);
        station.getPrev().forEach(equalTime);
        station.getTransfer().forEach(equalTime);
        return edges;
    }

    @Override
    public Deque<Node<StationID>> route(final StationID sourceId, final StationID targetId) {
        return new RouteRequest(sourceId, targetId)
                .timeToTransfer((source, target) -> 0)
                .find();
    }

    @Override
    public Deque<Node<StationID>> fastestRoute(final StationID sourceId, final StationID targetId) {
        return new RouteRequest(sourceId, targetId)
                .timeToNext((source, target) -> source.getTime())
                .timeToPrev((source, target) -> getMetroStation(target).getTime())
                .timeToTransfer((source, target) -> TRANSFER_TIME)
                .find();
    }


    private class RouteRequest {
        private final StationID source;
        private final StationID target;
        private TimeFunction next = (s, t) -> 1;
        private TimeFunction prev = (s, t) -> 1;
        private TimeFunction tran = (s, t) -> 1;
        private SearchAlgorithm<StationID> algorithm = new DijkstrasAlgorithm<>();

        private RouteRequest(final StationID source, final StationID target) {
            this.source = source;
            this.target = target;
        }

        public RouteRequest timeToNext(final TimeFunction next) {
            this.next = next;
            return this;
        }

        public RouteRequest timeToPrev(final TimeFunction prev) {
            this.prev = prev;
            return this;
        }

        public RouteRequest timeToTransfer(final TimeFunction tran) {
            this.tran = tran;
            return this;
        }

        public RouteRequest useAlgorithm(final SearchAlgorithm<StationID> searchAlgorithm) {
            this.algorithm = searchAlgorithm;
            return this;
        }

        Deque<Node<StationID>> find() {
            final var nodes = repository.stream().collect(toUnmodifiableMap(identity(), Node::new));
            nodes.values().forEach(node -> {
                final var s = getMetroStation(node.getId());
                s.getNext().forEach(t -> node.addEdge(nodes.get(t), next.apply(s, t)));
                s.getPrev().forEach(t -> node.addEdge(nodes.get(t), prev.apply(s, t)));
                s.getTransfer().forEach(t -> node.addEdge(nodes.get(t), tran.apply(s, t)));
            });
            final var sourceNode = Objects.requireNonNull(nodes.get(source));
            final var targetNode = Objects.requireNonNull(nodes.get(target));
            return algorithm.findRoute(sourceNode, targetNode);
        }
    }

}
