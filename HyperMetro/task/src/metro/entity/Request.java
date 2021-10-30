package metro.entity;

import java.util.List;

public class Request {
    private final String command;
    private final List<String> params;

    public Request(final String command, final List<String> params) {
        this.command = command;
        this.params = params;
    }

    public String command() {
        return command;
    }
}
