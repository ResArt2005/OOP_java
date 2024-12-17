package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic;

import javax.swing.*;
import java.awt.*;

public class ButtonsDesign {

    // Метод для создания стилизованной кнопки с эффектом наведения
    public static JButton createStyledButton(String text, Font font, Color BGColor, Color FGColor, Cursor cursorState) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(BGColor);
        button.setForeground(FGColor);
        button.setFocusPainted(true);
        button.setCursor(cursorState);

        return button;
    }
}
