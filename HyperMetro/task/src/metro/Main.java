package metro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SuppressWarnings("squid:S106")
public class Main {
    public static void main(String[] args) throws IOException {
        final var path = Paths.get(args[0]);

        if (Files.notExists(path)) {
            System.out.println("Error! Such a file doesn't exist!");
            return;
        }
        final var metroLine = new MetroMap(Files.readAllLines(path));
        final var printer = new Printer();
        printer.printMetroLine(metroLine.getLine());
    }

}
