package metro.command;

import lombok.AllArgsConstructor;
import metro.service.MetroService;

import java.util.List;

@AllArgsConstructor
abstract class HyperMetroCommand implements Command {
    final MetroService metroService;

    void validateParametersNumber(final List<String> parameters, final int requiredNumber) {
        if (parameters.size() != requiredNumber) {
            throw new IllegalArgumentException("Invalid number of parameters.");
        }
    }

}
