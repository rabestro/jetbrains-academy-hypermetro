package metro.command;

import metro.entity.Line;
import metro.entity.Metro;
import metro.ui.UserInterface;

public class Append extends ManageStation {
    public Append(final Metro metro, final UserInterface ui) {
        super(metro, ui, Line::append);
    }

}
