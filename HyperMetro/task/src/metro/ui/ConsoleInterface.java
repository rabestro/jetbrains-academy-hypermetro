package metro.ui;

import java.util.Scanner;

public class ConsoleInterface implements UserInterface {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    @SuppressWarnings("squid:S106")
    public void printLine(final String line) {
        System.out.println(line);
    }
}
