package metro;

import metro.services.MetroLoader;

import java.io.IOException;

@SuppressWarnings("squid:S106")
public class Main {
    public static void main(String[] args) {

/*        Gson gson = new Gson();
        final var metroLine = new MetroMap(Files.readAllLines(path));
        final var printer = new Printer();
        printer.printMetroLine(metroLine.getLine());*/

        try {
            new MetroCLI(
                    new MetroLoader()
                            .load(args[0])
            );
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
