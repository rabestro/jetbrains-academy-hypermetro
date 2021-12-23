package metro.algorithm;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;

public class DijkstrasAlgorithm<T> implements SearchAlgorithm<T> {

    @Override
    public Deque<Node<T>> findRoute(final Node<T> source, final Node<T> target) {
        final var queue = new LinkedList<Node<T>>();
        final var visited = new HashSet<Node<T>>();
        source.setDistance(0);
        queue.add(source);

        while (!queue.isEmpty()) {
            final var previous = queue.pollFirst();
            previous.getEdges().forEach((node, time) -> {
                final var distance = previous.getDistance() + time;
                if (!visited.contains(node)) {
                    queue.add(node);
                    visited.add(node);
                }
                if (distance < node.getDistance()) {
                    node.setPrevious(previous);
                    node.setDistance(distance);
                }
            });
        }
        return buildPath(target);
    }

}
