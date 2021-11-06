package metro;

import lombok.Value;
import metro.domain.MetroMap;
import metro.ui.UserInterface;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

@Value
@Component
public class AppCLI implements Consumer<MetroMap> {
    UserInterface ui;
    Predicate<String> exit;

    @Override
    public void accept(final MetroMap metroMap) {
        Stream.generate(ui::readLine)
                .takeWhile(not(exit))
                .forEach(ui::printLine);
    }
}
