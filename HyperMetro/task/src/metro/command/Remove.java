package metro.command;

import metro.entity.Line;
import metro.entity.Metro;
import metro.ui.UserInterface;

public class Remove extends ManageStation {
    public Remove(final Metro metro, final UserInterface ui) {
        super(metro, ui, Line::append);
    }

}
