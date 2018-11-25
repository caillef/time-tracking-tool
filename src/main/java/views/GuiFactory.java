package views;

import controllers.EntriesManager;
import models.Task;
import models.TimeEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class GuiFactory {
    public static JPanel AddLabelPane(JFrame frame, String text) {
        JLabel label = new JLabel(text);
        label.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
        pane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        pane.add(label);
        frame.add(pane);
        return pane;
    }

    public static JPanel AddTitle(JFrame frame, String title, int paddingLeft, int paddingRight) {
        JButton backButton = new JButton("\u25C4");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close window
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        JLabel label = new JLabel(title);
        label.setBorder(BorderFactory.createEmptyBorder(0, paddingLeft, 0, paddingRight));
        label.setForeground(new Color(255,255,255));

        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
        pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        pane.add(backButton);
        pane.add(label);
        pane.setBackground(new Color(100,100,100));
        frame.add(pane);
        return pane;
    }

    public static JPanel AddBottomRightButton(JFrame frame, String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pane.add(button);
        frame.add(pane);
        return pane;
    }

    public static ArrayList<JComboBox> AddHourDate(JFrame frame) {
        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pane.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
        pane.add(Box.createHorizontalGlue());

        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);

        // Picking hour
        String[] hours =  IntStream.rangeClosed(0, 23).boxed().map(e -> e.toString().length() > 1 ? e.toString() : '0' + e.toString()).collect(toList()).toArray(new String[0]);
        JComboBox hoursList = new JComboBox(hours);
        hoursList.setSelectedIndex(calendar.get(Calendar.HOUR_OF_DAY));
        pane.add(hoursList);
        pane.add(new JLabel("h"));
        String[] minutes =  IntStream.rangeClosed(0, 59).boxed().map(e -> e.toString().length() > 1 ? e.toString() : '0' + e.toString()).collect(toList()).toArray(new String[0]);
        JComboBox minutesList = new JComboBox(minutes);
        minutesList.setSelectedIndex(calendar.get(Calendar.MINUTE));
        pane.add(minutesList);
        pane.add(new JLabel("min"));

        // Picking day
        String[] days =  IntStream.rangeClosed(1, 31).boxed().map(e -> e.toString().length() > 1 ? e.toString() : '0' + e.toString()).collect(toList()).toArray(new String[0]);
        JComboBox daysList = new JComboBox(days);
        daysList.setSelectedIndex(calendar.get(Calendar.DAY_OF_MONTH) - 1);
        pane.add(daysList);
        pane.add(new JLabel("-"));
        String[] months =  IntStream.rangeClosed(1, 12).boxed().map(e -> e.toString().length() > 1 ? e.toString() : '0' + e.toString()).collect(toList()).toArray(new String[0]);
        JComboBox monthsList = new JComboBox(months);
        monthsList.setSelectedIndex(calendar.get(Calendar.MONTH));
        pane.add(monthsList);
        pane.add(new JLabel("-"));
        String[] years = IntStream.rangeClosed(2018, 2100).boxed().map(e -> e.toString()).collect(toList()).toArray(new String[0]);
        JComboBox yearsList = new JComboBox(years);
        yearsList.setSelectedIndex(0);
        pane.add(yearsList);
        frame.add(pane);

        ArrayList<JComboBox> list = new ArrayList<>();
        list.add(yearsList);
        list.add(monthsList);
        list.add(daysList);
        list.add(hoursList);
        list.add(minutesList);
        return list;
    }

    public static JTextField AddTextFieldWithLabel(JFrame frame, String title) {
        AddLabelPane(frame, title);
        JTextField taskField = new JTextField();
        taskField.setBorder(BorderFactory.createEmptyBorder(0,10,5,10));
        frame.add(taskField);
        return taskField;
    }

    public static void AddEntryDate(JFrame frame, Gui gui, String date, EntriesManager manager, UUID entryId) {
        JLabel label = new JLabel(date);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
        pane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        pane.add(label);
        pane.add(new EditEntryButton(manager, entryId, gui));
        pane.add(new DeleteEntryButton(manager, entryId, gui));
        pane.setBackground(getEntryColor(gui, manager, entryId));
        frame.add(pane);
    }

    public static void AddSeparator(JFrame frame) {
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
        JSeparator sep = new JSeparator();
        pane.add(sep);
        frame.add(pane);
    }

    private static String getHourFromDate(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm ");
        return dateFormatter.format(date);
    }

    private static String getDayMonthYearFromDate(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd ");
        return dateFormatter.format(date);
    }

    private static Color getEntryColor(Gui gui, EntriesManager manager, UUID entryId) {
        ArrayList<Task> list = gui.getTasksManager().getList();
        int i = 0;
        while (!list.get(i).getId().equals(manager.getById(entryId).getTaskId()))
            i++;
        int c = 230 - (170 / list.size()) * i;
        return new Color(c, c, c);
    }

    public static void AddTaskName(JFrame frame, String text, Gui gui, EntriesManager manager, UUID entryId) {
        JPanel pane = new JPanel();
        JLabel label = new JLabel(text);
        pane.add(label);
        pane.setBackground(getEntryColor(gui, manager, entryId));
        frame.add(pane);
    }

    private static String getMinutesFromMs(long ms) {
        String mn = "0" + ((ms / 60000) % 60);
        if (mn.length() > 2) {
            mn = "" + ((ms / 60000) % 60);
        }
        return mn;
    }

    public static void AddEntry(JFrame frame, Gui gui, TimeEntry entry, String taskName, EntriesManager manager, int index) {
        Date start = entry.getStart();
        Date end = entry.getEnd();
        UUID entryId = entry.getId();
        String date = getDayMonthYearFromDate(start) + getHourFromDate(start) + "to " + getDayMonthYearFromDate(end) + getHourFromDate(end);
        if (start.getDay() == end.getDay() && start.getMonth() == start.getMonth()) {
            date = getDayMonthYearFromDate(start) + getHourFromDate(start) + "to " + getHourFromDate(end);
        }
        date += "  " + ((end.getTime() - start.getTime()) / 3600000) + "h" + getMinutesFromMs(end.getTime() - start.getTime());
        AddEntryDate(frame, gui, date, manager, entryId);
        AddTaskName(frame, taskName, gui, manager, entryId);
    }

    public static ArrayList<JComboBox> AddHourDate(JFrame frame, Date date) {
        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pane.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
        pane.add(Box.createHorizontalGlue());

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);

        // Picking hour
        String[] hours =  IntStream.rangeClosed(0, 23).boxed().map(e -> e.toString().length() > 1 ? e.toString() : '0' + e.toString()).collect(toList()).toArray(new String[0]);
        JComboBox hoursList = new JComboBox(hours);
        hoursList.setSelectedIndex(calendar.get(Calendar.HOUR_OF_DAY));
        pane.add(hoursList);
        pane.add(new JLabel("h"));
        String[] minutes =  IntStream.rangeClosed(0, 59).boxed().map(e -> e.toString().length() > 1 ? e.toString() : '0' + e.toString()).collect(toList()).toArray(new String[0]);
        JComboBox minutesList = new JComboBox(minutes);
        minutesList.setSelectedIndex(calendar.get(Calendar.MINUTE));
        pane.add(minutesList);
        pane.add(new JLabel("min"));

        // Picking day
        String[] days =  IntStream.rangeClosed(1, 31).boxed().map(e -> e.toString().length() > 1 ? e.toString() : '0' + e.toString()).collect(toList()).toArray(new String[0]);
        JComboBox daysList = new JComboBox(days);
        daysList.setSelectedIndex(calendar.get(Calendar.DAY_OF_MONTH) - 1);
        pane.add(daysList);
        pane.add(new JLabel("-"));
        String[] months =  IntStream.rangeClosed(1, 12).boxed().map(e -> e.toString().length() > 1 ? e.toString() : '0' + e.toString()).collect(toList()).toArray(new String[0]);
        JComboBox monthsList = new JComboBox(months);
        monthsList.setSelectedIndex(calendar.get(Calendar.MONTH));
        pane.add(monthsList);
        pane.add(new JLabel("-"));
        String[] years = IntStream.rangeClosed(2018, 2100).boxed().map(e -> e.toString()).collect(toList()).toArray(new String[0]);
        JComboBox yearsList = new JComboBox(years);
        yearsList.setSelectedIndex(0);
        pane.add(yearsList);
        frame.add(pane);

        ArrayList<JComboBox> list = new ArrayList<>();
        list.add(yearsList);
        list.add(monthsList);
        list.add(daysList);
        list.add(hoursList);
        list.add(minutesList);
        return list;
    }
}
