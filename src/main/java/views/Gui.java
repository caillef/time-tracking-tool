package views;

import controllers.EntriesManager;
import controllers.TasksManager;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.HORIZONTAL;

public class Gui {
    private TasksManager tasksManager;
    private EntriesManager entriesManager;

    public static void main(String[] args) {
        new Gui();
    }

    public Gui() {
        SwingUtilities.invokeLater(this::showEntries);
        tasksManager = new TasksManager();
        entriesManager = new EntriesManager();
    }

    private void showEntries() {
        JFrame frame = new JFrame("Time Tracking Tool");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagLayout layout = new GridBagLayout();
        frame.setLayout(layout);

        GridBagConstraints c = new GridBagConstraints();

        JLabel label = new JLabel("This week");
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 1;
        c.gridy = 0;
        frame.add(label, c);

/*        try {
            String categories[] = { "Household", "Office", "Extended Family",
                "Company (US)", "Company (World)", "Team", "Will",
                "Birthday Card List", "High School", "Country", "Continent",
                "Planet" };
            JList list = new JList(categories);
            JScrollPane scrollableList = new JScrollPane(list);
            c.gridx = 0;
            c.gridy = 1;
            c.fill = HORIZONTAL;
            c.gridwidth = 3;
            frame.add(scrollableList,c);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        frame.pack();
        frame.setVisible(true);
    }
}
