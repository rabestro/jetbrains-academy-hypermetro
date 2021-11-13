package metro.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public interface SearchAlgorithm<T> {
    List<Node<T>> findRoute(T source, T target);

    default LinkedList<Node<T>> buildPath(Node<T> target) {
        final var path = new LinkedList<Node<T>>();
        Stream.iterate(target, Objects::nonNull, Node::getPrevious).forEach(path::addFirst);
        return path;
    }
}
