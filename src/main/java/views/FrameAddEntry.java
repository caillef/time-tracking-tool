package views;

import models.Task;
import models.TimeEntry;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class FrameAddEntry implements ActionListener {
    private Gui gui;
    private UUID taskId;
    private JComboBox comboBoxTasks;
    private JFrame frame;
    private TimeEntry defaultEntry;

    public FrameAddEntry(Gui gui) {
        this.gui = gui;
    }

    public FrameAddEntry(Gui gui, TimeEntry entry) {
        this.gui = gui;
        this.defaultEntry = entry;
    }

    private void setupFrame() {
        frame = new JFrame("Add a time entry");
        frame.setLayout(new java.awt.GridLayout(0, 1));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (defaultEntry != null) {
                    gui.getEntriesManager().addItem(defaultEntry);
                }
                gui.UpdateEntryList();
            }
        });
        frame.setResizable(false);
    }

    public void selectTask() {
        taskId = gui.getTasksManager().getList().get(comboBoxTasks.getSelectedIndex()).getId();
    }

    public void actionPerformed(ActionEvent e) {
        setupFrame();

        GuiFactory.AddTitle(frame, "Add Entry", 100,0);
        JPanel taskPane = GuiFactory.AddLabelPane(frame, "Task :");
        JButton addTaskButton = new JButton("Add");
        addTaskButton.addActionListener(new FrameAddTask(gui, this));
        taskPane.add(addTaskButton);

        // Task Picker
        String[] tasks = gui.getTasksManager().getList().stream().map(t -> t.getTitle()).collect(toList()).toArray(new String[0]);
        taskId = gui.getTasksManager().getList().get(0).getId();
        comboBoxTasks = new JComboBox(tasks);
        if (this.defaultEntry == null) {
            comboBoxTasks.setSelectedIndex(0);
        } else {
            ArrayList<Task> tasksList = gui.getTasksManager().getList();
            int index = 0;
            while (!tasksList.get(index).getId().equals(this.defaultEntry.getTaskId()))
                index++;
            comboBoxTasks.setSelectedIndex(index);
        }
        comboBoxTasks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectTask();
            }
        });
        comboBoxTasks.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
        frame.add(comboBoxTasks);

        GuiFactory.AddLabelPane(frame, "Start :");
        ArrayList<JComboBox> startDate;
        if (this.defaultEntry == null) {
            startDate = GuiFactory.AddHourDate(frame);
        } else {
            startDate = GuiFactory.AddHourDate(frame, defaultEntry.getStart());
        }
        GuiFactory.AddLabelPane(frame, "End :");
        ArrayList<JComboBox> endDate;
        if (this.defaultEntry == null) {
            endDate = GuiFactory.AddHourDate(frame);
        } else {
            endDate = GuiFactory.AddHourDate(frame, defaultEntry.getEnd());
        }
        GuiFactory.AddBottomRightButton(frame, "Add", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getDateFromPicker(endDate).getTime() < getDateFromPicker(startDate).getTime()) {
                    JOptionPane.showMessageDialog(frame, "Ending date is before starting date, try to fix those values.");
                    return;
                }
                TimeEntry entry = new TimeEntry(gui.getTasksManager(), taskId, getDateFromPicker(startDate), getDateFromPicker(endDate));
                defaultEntry = null;
                gui.getEntriesManager().addItem(entry);
                // Close window
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private Date getDateFromPicker(ArrayList<JComboBox> picker) {
        int[] elems = new int[5];
        for (int i = 0; i < 5; i++) {
            elems[i] = Integer.parseInt(picker.get(i).getSelectedItem().toString());
        }
        return new Date(elems[0] - 1900, elems[1] - 1, elems[2], elems[3], elems[4]);
    }

    public void AddTaskInList(Task task) {
        comboBoxTasks.addItem(task);
        comboBoxTasks.setSelectedIndex(comboBoxTasks.getItemCount() - 1);
    }
}
