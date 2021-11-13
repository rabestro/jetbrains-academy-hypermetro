package metro.algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.Stream;

public class DijkstrasAlgorithm<T> extends AbstractSearchAlgorithm<T> {

    public DijkstrasAlgorithm(final Stream<Node<T>> nodes) {
        super(nodes);
    }

    @Override
    public LinkedList<Node<T>> findRoute(final T source, final T target) {
        final var queue = new LinkedList<Node<T>>();
        final var visited = new HashSet<T>();
        final var sourceNode = getNode(source);
        final var targetNode = getNode(target);
        sourceNode.setDistance(0);
        queue.add(sourceNode);

        while (!queue.isEmpty()) {
            final var node = queue.pollFirst();
            final var neighbors = node.getNeighbors();
            neighbors.forEach((id, time) -> {
                final var distance = node.getDistance() + time;
                final var nextNode = getNode(id);
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
