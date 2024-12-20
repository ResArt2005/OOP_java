package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.filters;
import javax.swing.text.*;

// Фильтр документа для ограничения ввода только числами
public class IntNumericDocumentFilter extends DocumentFilter {
    private static final String NUMBER_REGEX = "[0-9]*";

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (isNumeric(string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (isNumeric(text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);
    }

    // Проверка, является ли введенное значение числом
    private boolean isNumeric(String text) {
        return text.matches(NUMBER_REGEX);
    }
}