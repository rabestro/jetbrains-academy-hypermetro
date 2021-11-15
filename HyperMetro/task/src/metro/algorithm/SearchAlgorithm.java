package metro.algorithm;

import java.util.Deque;

@FunctionalInterface
public interface SearchAlgorithm<T> {
    Deque<Node<T>> findRoute(Node<T> source, Node<T> target);
}
