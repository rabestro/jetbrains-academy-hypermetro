package metro.algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public class BreadthFirstSearchAlgorithm<T> extends AbstractSearchAlgorithm<T> {

    public BreadthFirstSearchAlgorithm(final Stream<Node<T>> nodes) {
        super(nodes);
    }

    public LinkedList<Node<T>> findRoute(T source, T target) {
        final var queue = new LinkedList<Node<T>>();
        final var visited = new HashSet<T>();
        final var sourceNode = getNode(source);

        sourceNode.setPrevious(null);
        queue.add(sourceNode);

        while (!queue.isEmpty()) {
            final var node = queue.pollFirst();
            if (target.equals(node.getId())) {
                return buildPath(node);
            }
            visited.add(node.getId());
            node.getNeighbors().keySet().stream()
                    .filter(not(visited::contains))
                    .map(this::getNode)
                    .map(node::setNextNode)
                    .forEach(queue::add);
        }
        return new LinkedList<>();
    }

}
