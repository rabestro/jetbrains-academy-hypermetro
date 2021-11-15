package metro.service;

import java.util.List;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toUnmodifiableList;

public class ParameterParser {
    private static final String WITHOUT_QUOTES = "(\\w+)";
    private static final String WITH_QUOTES = "\"([^\"]+)\"";

    private static final Pattern PARAMETERS_PATTERN =
            Pattern.compile(WITHOUT_QUOTES + "|" + WITH_QUOTES);

    private String extractParameter(final MatchResult result) {
        return Objects.requireNonNullElse(result.group(1), result.group(2));
    }

    public List<String> parse(final String parameters) {
        if (parameters == null) {
            return List.of();
        }
        return PARAMETERS_PATTERN
                .matcher(parameters)
                .results()
                .map(this::extractParameter)
                .collect(toUnmodifiableList());
    }

}
