package metro.algorithm;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Stream;

abstract class AbstractSearchAlgorithm<T> implements SearchAlgorithm<T> {

    Deque<Node<T>> buildPath(Node<T> target) {
        final var path = new LinkedList<Node<T>>();
        Stream.iterate(target, Objects::nonNull, Node::getPrevious).forEach(path::addFirst);
        return path;
    }
}
