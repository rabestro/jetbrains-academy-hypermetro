package metro.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public interface SearchAlgorithm<T> {
    List<T> findRoute(T source, T target);

    default LinkedList<T> buildPath(Node<T> target) {
        final var path = new LinkedList<T>();
        Stream.iterate(target, Objects::nonNull, Node::getPrevious)
                .map(Node::getId).forEach(path::addFirst);
        return path;
    }
}
