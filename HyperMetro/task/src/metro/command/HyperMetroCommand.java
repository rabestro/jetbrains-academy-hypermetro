package metro.command;

import lombok.AllArgsConstructor;
import metro.service.MetroService;

import java.util.List;
import java.util.regex.Pattern;

@AllArgsConstructor
abstract class HyperMetroCommand implements Command {
    private static final Pattern CAMEL_CASE = Pattern.compile("(\\p{Lower})(\\p{Upper})");
    static final String NOT_IMPLEMENTED = "This command is not yet implemented";

    final MetroService metroService;

    @Override
    public String name() {
        return CAMEL_CASE
                .matcher(this.getClass().getSimpleName())
                .replaceAll("$1-$2")
                .toLowerCase();
    }

    void validateParametersNumber(final List<String> parameters, final int requiredNumber) {
        if (parameters.size() != requiredNumber) {
            throw new IllegalArgumentException("Invalid number of parameters.");
        }
    }

}
