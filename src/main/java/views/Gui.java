package views;

import controllers.DatabaseManager;
import controllers.EntriesManager;
import controllers.TasksManager;
import models.TimeEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class Gui {
    private final DatabaseManager db;
    private final TasksManager tasksManager;
    private final EntriesManager entriesManager;
    private JFrame frame;

    public Gui() {
        this.db = new DatabaseManager();
        this.tasksManager = new TasksManager(db);
        db.setTasksManager(tasksManager);
        this.entriesManager = new EntriesManager(db);
        SwingUtilities.invokeLater(this::setupWindow);
    }

    private void setupWindow() {
        frame = new JFrame("Time Tracking Tool");
        frame.setLayout(new java.awt.GridLayout(0, 1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JLabel label = new JLabel("History");
        label.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 100));
        label.setForeground(new Color(255,255,255));
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
        pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        JButton addEntryButton = new JButton("+");
        addEntryButton.addActionListener(new FrameAddEntry(this));
        pane.add(label);
        pane.add(addEntryButton);
        pane.setBackground(new Color(100,100,100));
        frame.add(pane);
        showEntries();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showEntries() {
        this.entriesManager.getList().sort((a,b) -> (int) (a.getStart().getTime() - b.getStart().getTime() + (a.getEnd().getTime() - b.getEnd().getTime())));
        for (int i = 0; i < this.entriesManager.getList().size(); i++) {
            TimeEntry e = this.entriesManager.getList().get(i);
            String taskName = tasksManager.getById(e.getTaskId()).getTitle();
            GuiFactory.AddEntry(frame, this, e, taskName, entriesManager, i);
        }
    }

    public TasksManager getTasksManager() {
        return tasksManager;
    }

    public EntriesManager getEntriesManager() {
        return entriesManager;
    }

    public void UpdateEntryList() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        SwingUtilities.invokeLater(this::setupWindow);
    }
}
