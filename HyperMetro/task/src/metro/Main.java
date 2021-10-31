package metro;

import metro.services.MetroLoader;
import metro.ui.ConsoleInterface;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        final var ui = new ConsoleInterface();

        try {
            new MetroCLI(new MetroLoader().load(args[0]), ui);
        } catch (IOException e) {
            ui.printLine(e.getMessage());
        }

    }

}
