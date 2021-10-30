package metro.entity;

import java.util.Set;

public class Metro {
    private final Set<Line> lines;

    public Metro(final Set<Line> lines) {
        this.lines = lines;
    }
}
