package views;

import controllers.EntriesManager;
import models.TimeEntry;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class DeleteEntryButton extends JButton {
    public DeleteEntryButton(EntriesManager manager, UUID entryId, Gui gui) {
        super("X");
        this.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   TimeEntry entry = manager.getById(entryId);
                   manager.deleteItem(entry);
                   gui.UpdateEntryList();
               }
           });
    }
}
