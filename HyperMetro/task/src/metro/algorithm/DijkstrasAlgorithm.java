package metro.algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

public class DijkstrasAlgorithm<T> implements SearchAlgorithm<T> {
    private final Map<T, Node<T>> nodes;

    public DijkstrasAlgorithm(final Stream<Node<T>> nodes) {
        this.nodes = nodes.collect(toUnmodifiableMap(Node::getId, identity()));
    }

    @Override
    public LinkedList<Node<T>> findRoute(final T source, final T target) {
        final var sourceNode = requireNonNull(nodes.get(source));
        final var targetNode = requireNonNull(nodes.get(target));
        final var visited = new HashSet<T>();
        final var queue = new LinkedList<Node<T>>();
        sourceNode.setDistance(0);
        queue.add(sourceNode);

        while (!queue.isEmpty()) {
            final var node = queue.pollFirst();
            final var neighbors = node.getNeighbors();
            neighbors.forEach((id, time) -> {
                final var distance = node.getDistance() + time;
                final var nextNode = nodes.get(id);
                if (!visited.contains(id)) {
                    queue.add(nextNode);
                    visited.add(id);
                }
                if (distance < nextNode.getDistance()) {
                    nextNode.setPrevious(node);
                    nextNode.setDistance(distance);
                }
            });
        }
        return buildPath(targetNode);
    }
}
