package metro;

import metro.domain.MetroMap;
import metro.ui.ConsoleInterface;

import java.io.IOException;

public class Stage4 {
    public static void main(String[] args) throws IOException {
        final var ui = new ConsoleInterface();

        var metro = MetroMap.from(args[0]);

        ui.printLine(metro.getLines());
    }
}
