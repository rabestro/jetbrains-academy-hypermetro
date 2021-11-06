package metro.action;

import metro.domain.MetroMap;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public interface Action extends BiConsumer<MetroMap, List<String>> {
    Pattern CAMEL_CASE = Pattern.compile("(\\p{Lower})(\\p{Upper})");

    default String name() {
        return CAMEL_CASE
                .matcher(this.getClass().getSimpleName())
                .replaceAll("$1-$2")
                .toLowerCase();
    }

    static void checkParametersNumber(final List<String> parameters, final int number) {
        if (parameters.size() != number) {
            throw new IllegalArgumentException(
                    "Invalid number of parameters. Expected: " + number + ", actual: " + parameters.size());
        }
    }
}
