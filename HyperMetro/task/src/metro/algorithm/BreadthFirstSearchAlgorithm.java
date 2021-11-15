package metro.algorithm;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;

import static java.util.function.Predicate.not;

public class BreadthFirstSearchAlgorithm<T> extends AbstractSearchAlgorithm<T> {

    public Deque<Node<T>> findRoute(Node<T> source, Node<T> target) {
        final var queue = new LinkedList<Node<T>>();
        final var visited = new HashSet<Node<T>>();

        source.setPrevious(null);
        queue.add(source);

        while (!queue.isEmpty()) {
            final var node = queue.pollFirst();
            if (target.equals(node)) {
                return buildPath(target);
            }
            visited.add(node);
            node.getEdges().keySet().stream()
                    .filter(not(visited::contains))
                    .map(node::setNextNode)
                    .forEach(queue::add);
        }
        return new LinkedList<>();
    }

}
