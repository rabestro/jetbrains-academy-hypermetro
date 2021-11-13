package metro.algorithm;

import java.util.LinkedList;

@FunctionalInterface
public interface SearchAlgorithm<T> {
    LinkedList<Node<T>> findRoute(T source, T target);
}
