package views;

import controllers.EntriesManager;
import models.TimeEntry;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class EditEntryButton extends JButton {
    public EditEntryButton(EntriesManager manager, UUID entryId, Gui gui) {
        super("Edit \uD83D\uDD8A");
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimeEntry entry = manager.getById(entryId);
                manager.deleteItem(entry);
                (new FrameAddEntry(gui, entry)).actionPerformed(null);
            }
        });
    }
}

