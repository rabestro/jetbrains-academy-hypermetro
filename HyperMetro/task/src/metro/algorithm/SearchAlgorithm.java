package metro.algorithm;

import java.util.List;

public interface SearchAlgorithm<T> {
    List<T> findRoute(T source, T target);
}
