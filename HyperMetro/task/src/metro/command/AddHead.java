package metro.command;

import metro.entity.Line;
import metro.entity.Metro;
import metro.ui.UserInterface;

public class AddHead extends ManageStation {
    public AddHead(final Metro metro, final UserInterface ui) {
        super(metro, ui, Line::addHead);
    }

}
