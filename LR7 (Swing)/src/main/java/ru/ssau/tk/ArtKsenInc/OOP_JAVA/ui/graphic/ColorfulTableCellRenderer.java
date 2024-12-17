package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ColorfulTableCellRenderer extends DefaultTableCellRenderer {
    private final Color xColor; // Цвет для столбца X
    private final Color yColor; // Цвет для столбца Y
    private final Color textColor;
    private final String textFont;

    public ColorfulTableCellRenderer(Color xColor, Color yColor, Color textColor, String textFont) {
        this.xColor = xColor;
        this.yColor = yColor;
        this.textColor = textColor;
        this.textFont = textFont;
    }

    public ColorfulTableCellRenderer(Color xColor, Color yColor, Color textColor) {
        this(xColor, yColor, textColor, "Open Sans");
    }

    public ColorfulTableCellRenderer(Color xColor, Color yColor) {
        this(xColor, yColor, Color.BLACK);
    }

    public ColorfulTableCellRenderer() {
        this(Color.CYAN, Color.CYAN, Color.BLACK);
    }

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
        cell.setForeground(textColor);
        cell.setFont(new Font(textFont, Font.PLAIN, 15));

        // Применение цвета даже если ячейка пуста
        if (value == null || value.toString().isEmpty()) {
            cell.setForeground(Color.GRAY); // Например, серый цвет для пустых ячеек
        }

        return cell;
    }
}