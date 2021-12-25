package metro.algorithm;

import java.util.HashMap;
import java.util.Map;

public class Node<T> {
    static final int CONSTANT_DISTANCE = 1;

    private final T id;
    private final Map<Node<T>, Integer> edges = new HashMap<>();

    private Node<T> previous;
    private int distance = Integer.MAX_VALUE;

    public Node(final T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    Node<T> setNextNode(final Node<T> nextNode) {
        nextNode.setPrevious(this);
        return nextNode;
    }

    Node<T> getPrevious() {
        return previous;
    }

    void setPrevious(final Node<T> node) {
        previous = node;
    }

    public Map<Node<T>, Integer> getEdges() {
        return edges;
    }

    public void addEdge(final Node<T> node) {
        edges.put(node, CONSTANT_DISTANCE);
    }

    public void addEdge(final Node<T> node, final Integer distance) {
        edges.put(node, distance);
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(final int distance) {
        this.distance = distance;
    }

}
