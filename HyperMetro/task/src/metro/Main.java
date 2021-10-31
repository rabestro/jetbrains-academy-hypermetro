package metro;

import metro.command.Append;
import metro.command.Command;
import metro.command.Output;
import metro.entity.Metro;
import metro.services.MetroLoader;
import metro.ui.ConsoleInterface;

import java.io.IOException;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        final var ui = new ConsoleInterface();
        final Metro metro;
        try {
            metro = new MetroLoader().load(args[0]);
        } catch (IOException e) {
            ui.printLine(e.getMessage());
            return;
        }
        final var commands = Set.<Command>of(
                new Output(metro, ui),
                new Append(metro)
        );

        new MetroCLI(metro, ui, commands);

    }

}
