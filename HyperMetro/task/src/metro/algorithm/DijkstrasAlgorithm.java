package metro.algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

public class DijkstrasAlgorithm<T> implements SearchAlgorithm<T> {
    private final Map<T, Node<T>> nodes;

    public DijkstrasAlgorithm(final Set<Node<T>> nodes) {
        this.nodes = nodes.stream().collect(toUnmodifiableMap(Node::getId, identity()));
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
                final var neighbor = nodes.get(id);
                if (visited.contains(id)) {
                    queue.add(neighbor);
                    visited.add(id);
                }
                if (distance < neighbor.getDistance()) {
                    neighbor.setPrevious(node);
                    neighbor.setDistance(distance);
                }
            });
        }
        return buildPath(targetNode);
    }
}
