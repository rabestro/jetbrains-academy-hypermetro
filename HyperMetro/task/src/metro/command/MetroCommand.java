package metro.command;

import metro.entity.Metro;

import java.util.regex.Pattern;

abstract class MetroCommand implements Command {
    private static final Pattern CAMEL_CASE = Pattern.compile("(\\p{Lower})(\\p{Upper})");

    protected final Metro metro;

    MetroCommand(final Metro metro) {
        this.metro = metro;
    }

    @Override
    public String name() {
        return CAMEL_CASE
                .matcher(this.getClass().getSimpleName())
                .replaceAll("$1-$2")
                .toLowerCase();
    }
}
