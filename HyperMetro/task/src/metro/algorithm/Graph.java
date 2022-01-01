package metro.algorithm;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * A generic graph representation
 *
 * @param <T> type of vertex id
 */
public record Graph<T>(Map<T, Map<T, Number>> nodes) {
    /**
     * Edges of the given vertex
     *
     * @param id vertex (node)
     * @return all connections for the given vertex id
     */
    public Map<T, Number> edges(T id) {
        return nodes().get(id);
    }

    /**
     * Returns the calculated distance for the given path
     *
     * @param path the list of vertex ids representing the path
     * @return distance for the given path as double
     * @throws NullPointerException if path is incorrect and contains more than one vertex
     */
    public double getDistance(final List<T> path) {
        return IntStream.range(1, path.size())
                .mapToObj(i -> edges(path.get(i - 1)).get(path.get(i)))
                .mapToDouble(Number::doubleValue)
                .sum();
    }
}
