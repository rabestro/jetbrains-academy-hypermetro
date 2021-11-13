package metro.algorithm;

public abstract class Vertex<T> extends Node<T> {
    private int distance = Integer.MAX_VALUE;

    protected Vertex(final T id) {
        super(id);
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(final int distance) {
        this.distance = distance;
    }

}
