package views;

import javax.swing.*;
import java.awt.*;

public class TimeEntryCellRenderer extends JLabel implements ListCellRenderer {
    public TimeEntryCellRenderer(){
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        String s = value.toString();
        setText(s);
        return this;
    }
}
