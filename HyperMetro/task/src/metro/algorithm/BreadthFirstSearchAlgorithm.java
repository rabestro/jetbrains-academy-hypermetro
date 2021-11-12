package metro.algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public class BreadthFirstSearchAlgorithm<T> implements SearchAlgorithm<T> {
    private final Map<T, Node<T>> nodes;

    public BreadthFirstSearchAlgorithm(final Map<T, Node<T>> nodes) {
        this.nodes = nodes;
    }

    public LinkedList<T> findRoute(T source, T target) {
        final var queue = new LinkedList<Node<T>>();
        final var visited = new HashSet<T>();

        final var sourceNode = nodes.get(source);
        sourceNode.setPrevious(null);
        queue.add(sourceNode);

        while (!queue.isEmpty()) {
            final var step = queue.pollFirst();
            if (step.getId().equals(target)) {
                return buildPath(step);
            }
            visited.add(step.getId());
            step.getNeighbors().stream()
                    .filter(not(visited::contains))
                    .map(nodes::get)
                    .map(step::setNextStep)
                    .forEach(queue::add);
        }
        return new LinkedList<>();
    }

    private LinkedList<T> buildPath(Node<T> step) {
        final var path = new LinkedList<T>();
        Stream.iterate(step, Objects::nonNull, Node::getPrevious)
                .map(Node::getId).forEach(path::addFirst);
        return path;
    }
}
