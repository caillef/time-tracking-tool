package views;

import models.Task;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class FrameAddTask implements ActionListener {
    private FrameAddEntry source;
    private Gui gui;
    private JFrame frame;

    public FrameAddTask(Gui gui, FrameAddEntry source) {
        this.gui = gui;
        this.source = source;
        setupFrame();
        addElementsInFrame();
        frame.pack();
    }

    private void setupFrame() {
        frame = new JFrame("Add a task");
        frame.setLayout(new java.awt.GridLayout(0, 1));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
    }

    private void addElementsInFrame() {
        GuiFactory.AddTitle(frame, "Add Task",60, 90);
        JTextField taskField = GuiFactory.AddTextFieldWithLabel(frame, "Task name :");
        JTextField detailsField = GuiFactory.AddTextFieldWithLabel(frame, "Task details :");

        GuiFactory.AddBottomRightButton(frame, "Add", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = taskField.getText();
                String details = detailsField.getText();
                if (title.length() == 0) {
                    taskField.setText("Add a name for your task here...");
                    return;
                }
                Task newTask = new Task(title, details);
                gui.getTasksManager().addItem(newTask);

                source.AddTaskInList(newTask);
                // Close window
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        frame.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        frame.setVisible(true);
    }

}
