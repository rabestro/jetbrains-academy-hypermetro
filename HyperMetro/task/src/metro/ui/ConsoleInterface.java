package metro.ui;

import java.util.Scanner;

public class ConsoleInterface implements UserInterface {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String read() {
        return scanner.nextLine();
    }

    @Override
    @SuppressWarnings("squid:S106")
    public void write(final Object line) {
        System.out.println(line.toString());
    }
}
