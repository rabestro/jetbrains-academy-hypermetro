package metro.algorithm;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Stream;

@FunctionalInterface
public interface SearchAlgorithm<T> {
    /**
     * Finds the route from the source node to the target node.
     *
     * @param source node
     * @param target node
     * @return the route from the source node to the target node.
     */
    Deque<Node<T>> findRoute(Node<T> source, Node<T> target);

    default Deque<Node<T>> buildPath(final Node<T> target) {
        final var path = new LinkedList<Node<T>>();
        Stream.iterate(target, Objects::nonNull, Node::getPrevious).forEach(path::addFirst);
        return path;
    }
}
