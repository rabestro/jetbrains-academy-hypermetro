package metro.services;

import metro.command.Command;

import java.util.List;

public class Request implements Runnable {
    private final Command command;
    private final List<String> params;

    public Request(final Command command, final List<String> params) {
        this.command = command;
        this.params = params;
    }

    @Override
    public void run() {
        command.accept(params);
    }
}
