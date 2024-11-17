package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ColorfulTableCellRenderer extends DefaultTableCellRenderer {
    private Color xColor = Color.CYAN; // Цвет для столбца X
    private Color yColor = Color.CYAN; // Цвет для столбца Y

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (column == 0) { // Столбец X
            cell.setBackground(xColor);
        } else if (column == 1) { // Столбец Y
            cell.setBackground(yColor);
        }
        // Установка цвета текста
        cell.setForeground(Color.BLACK);
        return cell;
    }
}
