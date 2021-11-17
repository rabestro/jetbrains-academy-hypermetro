package metro.algorithm;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Stream;

@FunctionalInterface
public interface SearchAlgorithm<T> {
    Deque<Node<T>> findRoute(Node<T> source, Node<T> target);

    default Deque<Node<T>> buildPath(final Node<T> target) {
        final var path = new LinkedList<Node<T>>();
        Stream.iterate(target, Objects::nonNull, Node::getPrevious).forEach(path::addFirst);
        return path;
    }
}
