package metro.algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableMap;

public class BreadthFirstSearchAlgorithm<T> implements SearchAlgorithm<T> {
    private final Map<T, Node<T>> nodes;

    public BreadthFirstSearchAlgorithm(final Set<Node<T>> nodes) {
        this.nodes = nodes.stream().collect(toUnmodifiableMap(Node::getId, identity()));
    }

    public LinkedList<Node<T>> findRoute(T source, T target) {
        final var queue = new LinkedList<Node<T>>();
        final var visited = new HashSet<T>();

        final var sourceNode = nodes.get(source);
        sourceNode.setPrevious(null);
        queue.add(sourceNode);

        while (!queue.isEmpty()) {
            final var node = queue.pollFirst();
            if (node.getId().equals(target)) {
                return buildPath(node);
            }
            visited.add(node.getId());
            node.getNeighbors().keySet().stream()
                    .filter(not(visited::contains))
                    .map(nodes::get)
                    .map(node::setNextNode)
                    .forEach(queue::add);
        }
        return new LinkedList<>();
    }

}
