package metro.algorithm;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public record Graph<T>(Map<T, Map<T, Number>> nodes) {
    public Map<T, Number> edges(T id) {
        return nodes().get(id);
    }

    public double getDistance(final List<T> path) {
        return IntStream.range(1, path.size())
                .mapToObj(i -> edges(path.get(i - 1)).get(path.get(i)))
                .mapToDouble(Number::doubleValue)
                .sum();
    }
}
