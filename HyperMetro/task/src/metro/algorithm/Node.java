package metro.algorithm;

import java.util.Map;

public abstract class Node<T> {
    private final T id;
    private Node<T> previous;
    private int distance = Integer.MAX_VALUE;

    protected Node(final T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    Node<T> setNextNode(Node<T> nextNode) {
        nextNode.setPrevious(this);
        return nextNode;
    }

    Node<T> getPrevious() {
        return previous;
    }

    void setPrevious(Node<T> node) {
        previous = node;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(final int distance) {
        this.distance = distance;
    }

    protected abstract Map<T, Integer> getNeighbors();

}
