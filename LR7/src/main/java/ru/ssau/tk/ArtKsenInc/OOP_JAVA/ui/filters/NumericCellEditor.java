package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.text.AbstractDocument;
import java.awt.*;

// Класс для создания редактора ячеек, который принимает только числа
public class NumericCellEditor extends AbstractCellEditor implements TableCellEditor {
    private final JTextField textField;

    public NumericCellEditor() {
        textField = new JTextField();
        // Применяем NumericDocumentFilter, чтобы разрешать только числовой ввод
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DoubleNumericDocumentFilter());
    }

    @Override
    public Object getCellEditorValue() {
        try {
            // Возвращаем числовое значение
            return Double.parseDouble(textField.getText());
        } catch (NumberFormatException e) {
            return 0.0; // Возвращаем 0, если ввод некорректен
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Устанавливаем текущее значение в поле ввода
        textField.setText(value != null ? value.toString() : "");
        return textField;
    }
}