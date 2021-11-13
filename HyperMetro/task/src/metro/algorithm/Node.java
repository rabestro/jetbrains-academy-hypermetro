package metro.algorithm;

import java.util.Map;
import java.util.Objects;

public abstract class Node<T> {
    private final T id;
    private Node<T> previous;

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

    protected abstract Map<T, Integer> getNeighbors();

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Node<?> node = (Node<?>) o;
        return id.equals(node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
