package metro.algorithm;

import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

abstract class AbstractSearchAlgorithm<T> implements SearchAlgorithm<T> {
    private final Map<T, Node<T>> nodes;

    AbstractSearchAlgorithm(final Stream<Node<T>> nodes) {
        this.nodes = nodes.collect(toUnmodifiableMap(Node::getId, identity()));
    }

    Node<T> getNode(final T id) {
        return nodes.get(id);
    }

    LinkedList<Node<T>> buildPath(Node<T> target) {
        final var path = new LinkedList<Node<T>>();
        Stream.iterate(target, Objects::nonNull, Node::getPrevious).forEach(path::addFirst);
        return path;
    }
}
