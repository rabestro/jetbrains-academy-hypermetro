package metro;

import java.nio.file.Path;

@SuppressWarnings("squid:S106")
public class Main {
    public static void main(String[] args) {
        final var file = Path.of(args[0]);

        System.out.println(file.getFileName());
    }
}
