package metro.command;

import lombok.AllArgsConstructor;
import metro.model.MetroMap;
import metro.ui.UserInterface;

import java.util.List;

@AllArgsConstructor
public class Request implements Runnable {
    private final UserInterface ui;
    private final Action action;
    private final MetroMap metroMap;
    private final List<String> parameters;

    @Override
    public void run() {
        try {
            action.accept(metroMap, parameters);
        } catch (IllegalArgumentException exception) {
            ui.printLine(exception.getMessage());
        }
    }
}
