package metro.action;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class ExitPredicate implements Predicate<String> {
    @Override
    public boolean test(final String userInput) {
        return "/exit".equalsIgnoreCase(userInput);
    }
}
