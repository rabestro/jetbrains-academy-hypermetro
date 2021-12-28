package metro.algorithm;

import java.util.Map;

public record Graph<T>(Map<T, Map<T, Number>> nodes) {
    public Map<T, Number> edges(T id) {
        return nodes().get(id);
    }
}
