package metro.action;

import lombok.AllArgsConstructor;
import metro.ui.UserInterface;

@AllArgsConstructor
public class InvalidCommand implements Runnable {
    private final UserInterface ui;

    @Override
    public void run() {
        ui.printLine("Invalid command.");
    }
}
