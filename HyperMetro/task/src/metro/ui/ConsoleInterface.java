package metro.ui;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInterface implements UserInterface {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    @SuppressWarnings("squid:S106")
    public void printLine(final Object line) {
        System.out.println(line.toString());
    }
}
