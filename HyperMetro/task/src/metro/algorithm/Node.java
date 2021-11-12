package metro.algorithm;

import java.util.Set;

public abstract class Node<T> {
    private final T id;
    private Node<T> previous;

    protected Node(final T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    Node<T> setNextStep(Node<T> nextStep) {
        nextStep.setPrevious(this);
        return nextStep;
    }

    Node<T> getPrevious() {
        return previous;
    }

    void setPrevious(Node<T> node) {
        previous = node;
    }

    protected abstract Set<T> getNeighbors();

}
